package at.fhv.se.hotel.integration.application;

import at.fhv.se.hotel.application.api.InvoiceListingService;
import at.fhv.se.hotel.application.api.exception.GuestNotFoundException;
import at.fhv.se.hotel.application.api.exception.InvoiceNotFoundException;
import at.fhv.se.hotel.application.dto.InvoiceDTO;
import at.fhv.se.hotel.application.dto.InvoiceListingDTO;
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
import at.fhv.se.hotel.domain.repository.InvoiceRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class InvoiceListingServiceTests {
    @Autowired
    InvoiceListingService invoiceListingService;

    @MockBean
    InvoiceRepository invoiceRepository;

    @Test
    public void given_3invoices_when_3invoicesinrepository_then_returnmatchinginvoices() {
        // given
        List<Service> servicesExpected = List.of(
                Service.create(
                        new ServiceId("1"),
                        new ServiceName("TV"),
                        new Price(new BigDecimal("100"))),
                Service.create(
                        new ServiceId("2"),
                        new ServiceName("Breakfast"),
                        new Price(new BigDecimal("100")))
        );

        List<Guest> guestsExpected = List.of(
                Guest.create(new GuestId("1"),
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
                ),
                Guest.create(new GuestId("2"),
                        new FullName("Ali", "Cinar"),
                        Gender.MALE,
                        new Address("Hochschulstraße",
                                "1", "Dornbirn",
                                "6850", "Austria"),
                        LocalDate.of(1997, 8, 27),
                        "+43 676 123 456 789",
                        "ali.cinar@students.fhv.at",
                        0,
                        Collections.emptyList()
                )
        );

        List<Booking> bookingsExpected = List.of(
                Booking.create(
                        LocalDate.now(),
                        LocalDate.now().plusDays(10),
                        new BookingId("1"),
                        guestsExpected.get(0),
                        servicesExpected,
                        2,
                        1,
                        ""
                ),
                Booking.create(
                        LocalDate.now().plusDays(10),
                        LocalDate.now().plusDays(20),
                        new BookingId("2"),
                        guestsExpected.get(1),
                        servicesExpected,
                        2,
                        1,
                        ""
                ),
                Booking.create(
                        LocalDate.now().plusDays(50),
                        LocalDate.now().plusDays(60),
                        new BookingId("3"),
                        guestsExpected.get(1),
                        servicesExpected,
                        2,
                        1,
                        ""
                )
        );

        RoomCategory categoryExpected = RoomCategory.create(
                new RoomCategoryId("1"),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        Map<Room, Boolean> roomsExpected = Map.of(
                Room.create(new RoomName("single Room"), RoomStatus.FREE,categoryExpected), false,
                Room.create(new RoomName("double Room"),RoomStatus.FREE,categoryExpected), false
        );

        List<Room> roomsForInvoice = new ArrayList<>(roomsExpected.keySet());

        List<Stay> staysExpected = List.of(
                Stay.create(bookingsExpected.get(0), roomsExpected),
                Stay.create(bookingsExpected.get(1), roomsExpected),
                Stay.create(bookingsExpected.get(2), roomsExpected)
        );

        Season winterSeason = Season.create(
                new SeasonId("1"),
                new SeasonName("Winter "),
                LocalDate.of(2021, 12, 1),
                LocalDate.of(2022, 1, 31)
        );

        List<RoomCategoryPrice> roomCategoryPricesExpected = List.of(
                RoomCategoryPrice.create(
                        new RoomCategoryPriceId("1"),
                        winterSeason,
                        categoryExpected,
                        new BigDecimal("300")
                )
        );

        List<Invoice> invoicesExpected = List.of(
                Invoice.create(
                        new InvoiceId("1"),
                        "20211208001",
                        staysExpected.get(0),
                        roomCategoryPricesExpected,
                        servicesExpected,
                        roomsForInvoice,
                        10,
                        new BigDecimal("0.76"),
                        new BigDecimal("1.52"),
                        new BigDecimal("0.10"),
                        new BigDecimal("400"),
                        new BigDecimal("4000"),
                        new BigDecimal("0"),
                        new BigDecimal("0"),
                        new BigDecimal("4000"),
                        new BigDecimal("4001.52"),
                        new BigDecimal("5001.52")
                ),
                Invoice.create(
                        new InvoiceId("2"),
                        "20211208002",
                        staysExpected.get(1),
                        roomCategoryPricesExpected,
                        servicesExpected,
                        roomsForInvoice,
                        12,
                        new BigDecimal("0.76"),
                        new BigDecimal("1.52"),
                        new BigDecimal("0.10"),
                        new BigDecimal("400"),
                        new BigDecimal("4000"),
                        new BigDecimal("0"),
                        new BigDecimal("0"),
                        new BigDecimal("4000"),
                        new BigDecimal("4001.52"),
                        new BigDecimal("5001.52")
                ),
                Invoice.create(
                        new InvoiceId("3"),
                        "20211208003",
                        staysExpected.get(2),
                        roomCategoryPricesExpected,
                        servicesExpected,
                        roomsForInvoice,
                        12,
                        new BigDecimal("0.76"),
                        new BigDecimal("1.52"),
                        new BigDecimal("0.10"),
                        new BigDecimal("400"),
                        new BigDecimal("4000"),
                        new BigDecimal("0"),
                        new BigDecimal("0"),
                        new BigDecimal("4000"),
                        new BigDecimal("4001.52"),
                        new BigDecimal("5001.52")
                )
        );


        Mockito.when(invoiceRepository.findAllInvoices()).thenReturn(invoicesExpected);

        // when
        List<InvoiceListingDTO> invoicesActual = invoiceListingService.allInvoices();

        // then
        assertEquals(invoicesExpected.size(), invoicesActual.size());

        for(int i = 0; i < invoicesExpected.size(); i++) {
            assertEquals(invoicesExpected.get(i).getInvoiceId().id(), invoicesActual.get(i).id());
            assertEquals(invoicesExpected.get(i).getInvoiceNumber(), invoicesActual.get(i).invoiceNumber());
            assertEquals(invoicesExpected.get(i).getStay().getGuest().getName().firstName(), invoicesActual.get(i).guestFirstName());
            assertEquals(invoicesExpected.get(i).getStay().getGuest().getName().lastName(), invoicesActual.get(i).guestLastName());
            assertEquals(invoicesExpected.get(i).getStay().getGuest().getAddress().streetName(), invoicesActual.get(i).streetName());
            assertEquals(invoicesExpected.get(i).getStay().getGuest().getAddress().streetNumber(), invoicesActual.get(i).streetNumber());
            assertEquals(invoicesExpected.get(i).getStay().getGuest().getAddress().zipCode(), invoicesActual.get(i).zipCode());
            assertEquals(invoicesExpected.get(i).getStay().getGuest().getAddress().city(), invoicesActual.get(i).city());
            assertEquals(invoicesExpected.get(i).getTotalGrossAmount(), new BigDecimal(invoicesActual.get(i).totalGrossAmount()));
            assertEquals(invoicesExpected.get(i).isPaid(), invoicesActual.get(i).isPaid());
        }
    }

    @Test
    public void given_invoiceInRepository_when_findInvoiceById_then_returnEqualsInvoice() throws InvoiceNotFoundException {
        // given
        InvoiceId invoiceIdExpected = new InvoiceId("1");
        String invoiceNoExpected = "20211208001";

        List<Service> servicesExpected = List.of(
                Service.create(
                        new ServiceId("1"),
                        new ServiceName("TV"),
                        new Price(new BigDecimal("100"))),
                Service.create(
                        new ServiceId("2"),
                        new ServiceName("Breakfast"),
                        new Price(new BigDecimal("100")))
        );

        Guest guestExpected = Guest.create(
                new GuestId("1"),
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

        Booking bookingExpected = Booking.create(
                LocalDate.of(2021, 12, 5),
                LocalDate.of(2021, 12, 10),
                new BookingId("1"),
                guestExpected,
                servicesExpected,
                2,
                1,
                ""
        );

        RoomCategory categoryExpected = RoomCategory.create(
                new RoomCategoryId("1"),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        Map<Room, Boolean> roomsExpected = Map.of(
                Room.create(new RoomName("101"), RoomStatus.FREE, categoryExpected), false,
                Room.create(new RoomName("102"),RoomStatus.FREE, categoryExpected), false
        );

        List<Room> roomsForInvoice = new ArrayList<>(roomsExpected.keySet());

        Stay stayExpected = Stay.create(bookingExpected, roomsExpected);

        Season winterSeason = Season.create(
                new SeasonId("1"),
                new SeasonName("Winter "),
                LocalDate.of(2021, 12, 1),
                LocalDate.of(2022, 1, 31)
        );

        List<RoomCategoryPrice> roomCategoryPricesExpected = List.of(
                RoomCategoryPrice.create(
                        new RoomCategoryPriceId("1"),
                        winterSeason,
                        categoryExpected,
                        new BigDecimal("300")
                )
        );

        Invoice invoiceExpected = Invoice.create(
                invoiceIdExpected,
                invoiceNoExpected,
                stayExpected,
                roomCategoryPricesExpected,
                servicesExpected,
                roomsForInvoice,
                10,
                new BigDecimal("0.76"),
                new BigDecimal("1.52"),
                new BigDecimal("0.10"),
                new BigDecimal("400"),
                new BigDecimal("4000"),
                new BigDecimal("0"),
                new BigDecimal("0"),
                new BigDecimal("4000"),
                new BigDecimal("4001.52"),
                new BigDecimal("5001.52")
        );

        Mockito.when(invoiceRepository.invoiceById(invoiceIdExpected)).thenReturn(Optional.of(invoiceExpected));

        // when
        InvoiceDTO invoiceActual = invoiceListingService.findInvoiceById(invoiceIdExpected.id());

        // then
        assertEquals(invoiceExpected.getInvoiceNumber(), invoiceActual.invoiceNumber());
        assertEquals(invoiceExpected.getInvoiceDate(), invoiceActual.invoiceDate());
        assertEquals(guestExpected.getName().firstName(), invoiceActual.guestFirstName());
        assertEquals(guestExpected.getName().lastName(), invoiceActual.guestLastName());
        assertEquals(guestExpected.getAddress().streetName(), invoiceActual.streetName());
        assertEquals(guestExpected.getAddress().streetNumber(), invoiceActual.streetNumber());
        assertEquals(guestExpected.getAddress().zipCode(), invoiceActual.zipCode());
        assertEquals(guestExpected.getAddress().city(), invoiceActual.city());
        assertEquals(bookingExpected.getAmountOfAdults(), invoiceActual.amountOfAdults());
        assertEquals(bookingExpected.getAmountOfChildren(), invoiceActual.amountOfChildren());
        assertEquals(invoiceExpected.getServices().size(), invoiceActual.services().size());
        assertEquals(invoiceExpected.getRooms().size(), invoiceActual.roomNames().size());
        assertEquals(invoiceExpected.getRoomCategoryPriceList().size(), invoiceActual.categoryPrices().size());
        assertEquals(bookingExpected.getCheckInDate(), invoiceActual.checkInDate());
        assertEquals(bookingExpected.getCheckOutDate(), invoiceActual.checkOutDate());
        assertEquals(invoiceExpected.getAmountOfNights(), invoiceActual.amountOfNights());
        assertEquals(invoiceExpected.getLocalTaxPerPerson(), invoiceActual.localTaxPerPerson());
        assertEquals(invoiceExpected.getLocalTaxTotal(), invoiceActual.localTaxTotal());
        assertEquals(invoiceExpected.getValueAddedTaxInPercent(), invoiceActual.valueAddedTaxInPercent());
        assertEquals(invoiceExpected.getValueAddedTaxInEuro(), invoiceActual.valueAddedTaxInEuro());
        assertEquals(invoiceExpected.getTotalNetAmountBeforeDiscount(), invoiceActual.totalNetAmountBeforeDiscount());
        assertEquals(invoiceExpected.getTotalNetAmountAfterDiscount(), invoiceActual.totalNetAmountAfterDiscount());
        assertEquals(invoiceExpected.getTotalNetAmountAfterLocalTax(), invoiceActual.totalNetAmountAfterLocalTax());
        assertEquals(invoiceExpected.getTotalGrossAmount(), invoiceActual.totalGrossAmount());
        assertEquals(invoiceExpected.getDiscountInPercent().doubleValue(), invoiceActual.discountInPercent());
        assertEquals(invoiceExpected.getDiscountInEuro(), invoiceActual.discountInEuro());
    }

    @Test
    public void given_missingInvoice_when_findInvoiceById_then_InvoiceNotFoundExceptionIsThrown() {
        // given
        InvoiceId invoiceIdExpected = new InvoiceId("1");

        Mockito.when(invoiceRepository.invoiceById(invoiceIdExpected)).thenReturn(Optional.empty());

        // when ... then
        Exception exception = assertThrows(InvoiceNotFoundException.class, () -> {
            invoiceListingService.findInvoiceById(invoiceIdExpected.id());
        });

        String expectedMessage = "Invoice with id " + invoiceIdExpected.id() + " not found";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
