package at.fhv.se.hotel.integration.application;

import at.fhv.se.hotel.application.api.InvoiceDownloadService;
import at.fhv.se.hotel.application.api.exception.InvoiceNotFoundException;
import at.fhv.se.hotel.application.api.exception.StayNotFoundException;
import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;
import at.fhv.se.hotel.domain.model.guest.*;
import at.fhv.se.hotel.domain.model.invoice.Invoice;
import at.fhv.se.hotel.domain.model.invoice.InvoiceId;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomName;
import at.fhv.se.hotel.domain.model.room.RoomStatus;
import at.fhv.se.hotel.domain.model.roomcategory.*;
import at.fhv.se.hotel.domain.model.season.Season;
import at.fhv.se.hotel.domain.model.season.SeasonId;
import at.fhv.se.hotel.domain.model.season.SeasonName;
import at.fhv.se.hotel.domain.model.service.Price;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.service.ServiceId;
import at.fhv.se.hotel.domain.model.service.ServiceName;
import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.model.stay.StayId;
import at.fhv.se.hotel.domain.repository.InvoiceRepository;
import org.apache.fop.apps.FOPException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class InvoiceDownloadServiceTests {
    @MockBean
    InvoiceRepository invoiceRepository;

    @Autowired
    InvoiceDownloadService invoiceDownloadService;

    @Test
    public void given_invoiceInRepo_when_downloadInvoice_then_nonNullByteArrayIsReturned() throws FOPException, InvoiceNotFoundException, JAXBException, IOException, TransformerException {
        // given
        InvoiceId invoiceIdExpected = new InvoiceId("1337");
        String invoiceNumberExpected = "20220101001";

        List<Service> servicesExpected = Arrays.asList(
                Service.create(new ServiceId("1"),
                        new ServiceName("TV"),
                        new Price(new BigDecimal("100"))),
                Service.create(new ServiceId("2"),
                        new ServiceName("Breakfast"),
                        new Price(new BigDecimal("100")))
        );

        Guest guestExpected = Guest.create(new GuestId("1"),
                new FullName("Michael", "Spiegel"),
                Gender.MALE,
                new Address("Hochschulstraße",
                        "1", "Dornbirn",
                        "6850", "Austria"),
                LocalDate.of(1999, 3, 20),
                "+43 660 123 456 789",
                "michael.spiegel@students.fhv.at",
                0,
                Collections.emptyList()
        );

        BookingId idExpected = new BookingId("1337");
        Booking bookingExpected = Booking.create(
                LocalDate.of(2021, 12, 5),
                LocalDate.of(2021, 12, 15),
                idExpected,
                guestExpected,
                servicesExpected,
                2,
                1,
                "Nothing"
        );
        List<RoomCategory> categoriesExpected = Arrays.asList(
                RoomCategory.create(new RoomCategoryId("1"),
                        new RoomCategoryName("Single Room"),
                        new Description("This is a single room")),
                RoomCategory.create(new RoomCategoryId("2"),
                        new RoomCategoryName("Double Room"),
                        new Description("This is a double room"))
        );
        bookingExpected.addRoomCategory(categoriesExpected.get(0), 1);
        bookingExpected.addRoomCategory(categoriesExpected.get(1), 1);


        String roomNameExpected = "Room 1";
        RoomStatus roomStatusExpected = RoomStatus.FREE;
        Map<Room, Boolean> roomsExpected = Map.of(
                Room.create(
                        new RoomName(roomNameExpected),
                        roomStatusExpected,
                        categoriesExpected.get(0)
                ), false
        );

        List<Room> roomsForInvoice = new ArrayList<>(roomsExpected.keySet());

        Stay stayExpected = Stay.create(new StayId("1"), bookingExpected, roomsExpected);

        Season winterSeason = Season.create(
                new SeasonId("1"),
                new SeasonName("Winter "),
                LocalDate.of(2021, 12, 1),
                LocalDate.of(2022, 1, 31)
        );

        List<RoomCategoryPrice> categoryPricesExpected = List.of(
                RoomCategoryPrice.create(
                        new RoomCategoryPriceId("1"),
                        winterSeason,
                        categoriesExpected.get(0),
                        new BigDecimal("300")
                ),
                RoomCategoryPrice.create(
                        new RoomCategoryPriceId("2"),
                        winterSeason,
                        categoriesExpected.get(1),
                        new BigDecimal("500")
                )
        );

        int amountOfNightsExpected = 9;
        BigDecimal localTaxPerPersonExpected = new BigDecimal("0.76");
        BigDecimal localTaxTotalExpected = new BigDecimal("1.52");
        BigDecimal valueAddedTaxInPercentExpected = new BigDecimal("0.10");
        BigDecimal valueAddedTaxInEuroExpected = new BigDecimal("100");
        BigDecimal totalNetAmountBeforeDiscountExpected = new BigDecimal("200");
        BigDecimal discountInPercentExpected = new BigDecimal("0");
        BigDecimal discountInEuroExpected = new BigDecimal("0");
        BigDecimal totalNetAmountAfterDiscountExpected = new BigDecimal("200");
        BigDecimal totalNetAmountAfterLocalTaxExpected = new BigDecimal("201.52");
        BigDecimal totalGrossAmountExpected = new BigDecimal("301.52");

        Invoice invoiceExpected = Invoice.create(
                invoiceIdExpected,
                invoiceNumberExpected,
                stayExpected,
                categoryPricesExpected,
                servicesExpected,
                roomsForInvoice,
                amountOfNightsExpected,
                localTaxPerPersonExpected,
                localTaxTotalExpected,
                valueAddedTaxInPercentExpected,
                valueAddedTaxInEuroExpected,
                totalNetAmountBeforeDiscountExpected,
                discountInPercentExpected,
                discountInEuroExpected,
                totalNetAmountAfterDiscountExpected,
                totalNetAmountAfterLocalTaxExpected,
                totalGrossAmountExpected
        );

        Mockito.when(invoiceRepository.invoiceByNumber(invoiceNumberExpected)).thenReturn(Optional.of(invoiceExpected));

        // when
        ByteArrayResource resultActual = invoiceDownloadService.download(invoiceNumberExpected);

        // then
        assertNotNull(resultActual);
        assertNotEquals(0, resultActual.contentLength());
    }

    @Test
    public void given_missingInvoice_when_downloadInvoice_InvoiceNotFoundExceptionIsThrown() {
        // given
        String invoiceNumberExpected = "1";

        Mockito.when(invoiceRepository.invoiceByNumber(invoiceNumberExpected)).thenReturn(Optional.empty());

        // when ... then
        Exception exception = assertThrows(InvoiceNotFoundException.class,
                () -> invoiceDownloadService.download(invoiceNumberExpected)
        );

        String expectedMessage = "Invoice with number " + invoiceNumberExpected + " not found";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
