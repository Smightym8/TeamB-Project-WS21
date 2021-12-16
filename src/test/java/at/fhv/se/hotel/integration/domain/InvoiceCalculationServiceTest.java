package at.fhv.se.hotel.integration.domain;

import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;
import at.fhv.se.hotel.domain.model.guest.*;
import at.fhv.se.hotel.domain.model.invoice.Invoice;
import at.fhv.se.hotel.domain.model.invoice.InvoiceId;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomStatus;
import at.fhv.se.hotel.domain.model.roomcategory.*;
import at.fhv.se.hotel.domain.model.service.Price;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.service.ServiceId;
import at.fhv.se.hotel.domain.model.service.ServiceName;
import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.repository.InvoiceRepository;
import at.fhv.se.hotel.domain.repository.RoomCategoryPriceRepository;
import at.fhv.se.hotel.domain.services.api.InvoiceCalculationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class InvoiceCalculationServiceTest {
    @Autowired
    InvoiceCalculationService invoiceCalculationService;

    @MockBean
    RoomCategoryPriceRepository roomCategoryPriceRepository;

    @MockBean
    InvoiceRepository invoiceRepository;

    @Test
    void given_invoicedetails_when_calculate_then_returnexpectedamount() {
        // given
        Guest guest = Guest.create(new GuestId("1"),
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

        RoomCategory category = RoomCategory.create(new RoomCategoryId("1"),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        List<Service> services = Arrays.asList(
                Service.create(new ServiceId("1"),
                        new ServiceName("TV"),
                        new Price(new BigDecimal("100"))),
                Service.create(new ServiceId("2"),
                        new ServiceName("Breakfast"),
                        new Price(new BigDecimal("100")))
        );

        Booking booking = Booking.create(
                LocalDate.of(2021, 11, 26),
                LocalDate.of(2021,11,29),
                new BookingId("1"),
                guest,
                services,
                2,
                1,
                ""
        );
        booking.addRoomCategory(category, 1);

        List<Room> rooms = List.of(
                Room.create("101", RoomStatus.FREE, category)
        );


        RoomCategoryPrice price = RoomCategoryPrice.create(
                new RoomCategoryPriceId("1"),
                Season.SUMMER,
                category,
                new BigDecimal("600")
        );

        Stay stayExpected = Stay.create(booking, rooms);

        String invoiceNumberExpected = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001";
        int amountOfNightsExpected = 3;
        BigDecimal localTaxPerPersonExpected = new BigDecimal("0.76");
        BigDecimal localTaxTotalExpected = new BigDecimal("1.52");
        BigDecimal valueAddedTaxInPercentExpected = new BigDecimal("0.1");
        BigDecimal totalNetAmountExpected = new BigDecimal("2001.52");
        BigDecimal valueAddedTaxInEuroExpected = new BigDecimal("200.0");
        BigDecimal totalGrossAmountExpected = new BigDecimal("2201.52");

        // when
        Mockito.when(invoiceRepository.invoicesByDate(LocalDate.now())).thenReturn(Collections.emptyList());

        Mockito.when(invoiceRepository.nextIdentity()).thenReturn(
            new InvoiceId(UUID.randomUUID().toString().toUpperCase())
        );

        Mockito.when(roomCategoryPriceRepository.priceBySeasonAndCategory(Season.SUMMER, category.getRoomCategoryId()))
                .thenReturn(java.util.Optional.of(price));

        Invoice invoice = invoiceCalculationService.calculateInvoice(stayExpected);

        // then
        assertEquals(invoiceNumberExpected, invoice.getInvoiceNumber());
        assertEquals(amountOfNightsExpected, invoice.getAmountOfNights());
        assertEquals(localTaxPerPersonExpected, invoice.getLocalTaxPerPerson());
        assertEquals(localTaxTotalExpected, invoice.getLocalTaxTotal());
        assertEquals(valueAddedTaxInPercentExpected, invoice.getValueAddedTaxInPercent());
        assertEquals(totalNetAmountExpected, invoice.getTotalNetAmount());
        assertEquals(valueAddedTaxInEuroExpected, invoice.getValueAddedTaxInEuro());
        assertEquals(totalGrossAmountExpected, invoice.getTotalGrossAmount());
    }
}
