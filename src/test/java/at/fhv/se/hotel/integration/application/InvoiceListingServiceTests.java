package at.fhv.se.hotel.integration.application;

import at.fhv.se.hotel.application.api.InvoiceListingService;
import at.fhv.se.hotel.application.dto.InvoiceDTO;
import at.fhv.se.hotel.application.dto.InvoiceListingDTO;
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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class InvoiceListingServiceTests {
    @Autowired
    InvoiceListingService invoiceListingService;

    @MockBean
    InvoiceRepository invoiceRepository;

    @Test
    public void given_3invoices_when_3invoicesinrepository_then_returnmatchinginvoices() {
        // given
        List<Service> servicesExpected = Arrays.asList(
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
                Room.create("single Room", RoomStatus.FREE,categoryExpected), false,
                Room.create("double Room",RoomStatus.FREE,categoryExpected), false
        );

        List<Stay> staysExpected = List.of(
                Stay.create(bookingsExpected.get(0), roomsExpected),
                Stay.create(bookingsExpected.get(1), roomsExpected),
                Stay.create(bookingsExpected.get(2), roomsExpected)
        );

        List<RoomCategoryPrice> roomCategoryPricesExpected = List.of(
                RoomCategoryPrice.create(
                        new RoomCategoryPriceId("1"),
                        Season.WINTER,
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
                        10,
                        new BigDecimal("0.76"),
                        new BigDecimal("1.52"),
                        new BigDecimal("0.1"),
                        new BigDecimal("4000"),
                        new BigDecimal("200"),
                        new BigDecimal("5001.52")
                ),
                Invoice.create(
                        new InvoiceId("2"),
                        "20211208002",
                        staysExpected.get(1),
                        roomCategoryPricesExpected,
                        servicesExpected,
                        12,
                        new BigDecimal("0.76"),
                        new BigDecimal("1.52"),
                        new BigDecimal("0.1"),
                        new BigDecimal("4000"),
                        new BigDecimal("200"),
                        new BigDecimal("5001.52")
                ),
                Invoice.create(
                        new InvoiceId("3"),
                        "20211208003",
                        staysExpected.get(2),
                        roomCategoryPricesExpected,
                        servicesExpected,
                        12,
                        new BigDecimal("0.76"),
                        new BigDecimal("1.52"),
                        new BigDecimal("0.1"),
                        new BigDecimal("4000"),
                        new BigDecimal("200"),
                        new BigDecimal("5001.52")
                )
        );


        Mockito.when(invoiceRepository.findAllInvoices()).thenReturn(invoicesExpected);

        // when
        List<InvoiceListingDTO> invoicesActual = invoiceListingService.allInvoices();

        // then
        assertEquals(invoicesExpected.size(), invoicesActual.size());
    }
}
