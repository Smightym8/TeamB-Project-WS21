package at.fhv.se.hotel.integration.infrastructure;

import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;
import at.fhv.se.hotel.domain.model.guest.Address;
import at.fhv.se.hotel.domain.model.guest.FullName;
import at.fhv.se.hotel.domain.model.guest.Gender;
import at.fhv.se.hotel.domain.model.guest.Guest;
import at.fhv.se.hotel.domain.model.invoice.Invoice;
import at.fhv.se.hotel.domain.model.invoice.InvoiceId;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomStatus;
import at.fhv.se.hotel.domain.model.roomcategory.Description;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryName;
import at.fhv.se.hotel.domain.model.service.Price;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.service.ServiceName;
import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class InvoiceRepositoryImplTests {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    StayRepository stayRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    GuestRepository guestRepository;

    @Autowired
    RoomCategoryRepository roomCategoryRepository;
    //void given_3invoices_when_fetchinvoices_then_returnequalinvoices
    @Test
    void given_invoice_when_invoicerepository_then_returnequalinvoice() {
        //given
        List<Service> servicesExpected = Arrays.asList(
        Service.create(serviceRepository.nextIdentity(),
                new ServiceName("TV"),
                new Price(new BigDecimal("100"))),
                    Service.create(serviceRepository.nextIdentity(),
                new ServiceName("Breakfast"),
                new Price(new BigDecimal("100")))
        );
        Guest guestExpected = Guest.create(guestRepository.nextIdentity(),
                new FullName("Michael", "Spiegel"),
                Gender.MALE,
                new Address("Hochschulstraße",
                        "1", "Dornbirn",
                        "6850", "Austria"),
                LocalDate.of(1999, 3, 20),
                "+43 660 123 456 789",
                "michael.spiegel@students.fhv.at",
                Collections.emptyList()
        );

        BookingId idExpected = new BookingId("1337");
        Booking bookingExpected = Booking.create(
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                idExpected,
                guestExpected,
                servicesExpected
        );
        List<RoomCategory> categoriesExpected = Arrays.asList(
                RoomCategory.create(roomCategoryRepository.nextIdentity(),
                        new RoomCategoryName("Single Room"),
                        new Description("This is a single room")),
                RoomCategory.create(roomCategoryRepository.nextIdentity(),
                        new RoomCategoryName("Double Room"),
                        new Description("This is a double room"))
        );
        bookingExpected.addRoomCategory(categoriesExpected.get(0), 1);
        bookingExpected.addRoomCategory(categoriesExpected.get(1), 1);

        String roomNameExpected = "Room 1";
        RoomStatus roomStatusExpected = RoomStatus.FREE;

        List<Room> roomsExpected =
                List.of(Room.create(
                        roomNameExpected,
                        roomStatusExpected,
                        categoriesExpected.get(0)));
        Stay stayExpected = Stay.create(bookingExpected, roomsExpected);

        InvoiceId invoiceIdExpected = new InvoiceId("1337");
        Invoice invoiceExpected = Invoice.create(invoiceIdExpected,
                            stayExpected);

        //when
        this.invoiceRepository.add(invoiceExpected);
        Invoice invoiceActual = this.invoiceRepository.invoiceById(invoiceIdExpected).get();

        //then
        assertEquals(invoiceExpected, invoiceActual);
    }

    @Test
    void platzhalter() {
        
    }
}
