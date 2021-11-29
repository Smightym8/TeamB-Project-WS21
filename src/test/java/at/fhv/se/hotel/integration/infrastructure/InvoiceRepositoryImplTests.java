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

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
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

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    private EntityManager em;

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

        List<Room> roomsExpected = List.of(
                Room.create(
                        roomNameExpected,
                        roomStatusExpected,
                        categoriesExpected.get(0)
                )
        );
        Stay stayExpected = Stay.create(bookingExpected, roomsExpected);
        BigDecimal amountExpected = new BigDecimal(1000);

        InvoiceId invoiceIdExpected = new InvoiceId("1337");
        Invoice invoiceExpected = Invoice.create(invoiceIdExpected,
                            stayExpected, amountExpected);

        //when
        servicesExpected.forEach(service -> this.serviceRepository.add(service));
        this.guestRepository.add(guestExpected);
        categoriesExpected.forEach(category -> this.roomCategoryRepository.add(category));
        this.bookingRepository.add(bookingExpected);
        this.roomRepository.add(roomsExpected.get(0));
        this.invoiceRepository.add(invoiceExpected);
        em.flush();
        Invoice invoiceActual = this.invoiceRepository.invoiceById(invoiceIdExpected).get();

        //then
        assertEquals(invoiceExpected, invoiceActual);
    }

    @Test
    void given_3invoices_when_fetchallinvoices_then_returnequalinvoices() {
        // given
        List<Guest> guestsExpected = List.of(
                Guest.create(guestRepository.nextIdentity(),
                        new FullName("Michael", "Spiegel"),
                        Gender.MALE,
                        new Address("Hochschulstraße",
                                "1", "Dornbirn",
                                "6850", "Austria"),
                        LocalDate.of(1999, 3, 20),
                        "+43 660 123 456 789",
                        "michael.spiegel@students.fhv.at",
                        Collections.emptyList()
                ),
                Guest.create(guestRepository.nextIdentity(),
                        new FullName("Ali", "Cinar"),
                        Gender.MALE,
                        new Address("Hochschulstraße",
                                "1", "Dornbirn",
                                "6850", "Austria"),
                        LocalDate.of(1997, 8, 27),
                        "+43 676 123 456 789",
                        "ali.cinar@students.fhv.at",
                        Collections.emptyList()
                ),
                Guest.create(guestRepository.nextIdentity(),
                        new FullName("Umut", "Caglayan"),
                        Gender.MALE,
                        new Address("Hochschulstraße",
                                "1", "Dornbirn",
                                "6850", "Austria"),
                        LocalDate.of(1999, 7, 7),
                        "+43 676 123 456 789",
                        "umut.caglayan@students.fhv.at",
                        Collections.emptyList()
                )
        );

        List<RoomCategory> categoriesExpected = Arrays.asList(
                RoomCategory.create(roomCategoryRepository.nextIdentity(),
                        new RoomCategoryName("Single Room"),
                        new Description("This is a single room")),
                RoomCategory.create(roomCategoryRepository.nextIdentity(),
                        new RoomCategoryName("Double Room"),
                        new Description("This is a double room"))
        );

        List<Service> servicesExpected = Arrays.asList(
                Service.create(serviceRepository.nextIdentity(),
                        new ServiceName("TV"),
                        new Price(new BigDecimal("100"))),
                Service.create(serviceRepository.nextIdentity(),
                        new ServiceName("Breakfast"),
                        new Price(new BigDecimal("100")))
        );
        List<Booking> bookingsExpected = List.of(
                Booking.create(
                        LocalDate.now(),
                        LocalDate.now().plusDays(10),
                        bookingRepository.nextIdentity(),
                        guestsExpected.get(0),
                        servicesExpected),
                Booking.create(
                        LocalDate.now(),
                        LocalDate.now().plusDays(10),
                        bookingRepository.nextIdentity(),
                        guestsExpected.get(1),
                        servicesExpected),
                Booking.create(
                        LocalDate.now(),
                        LocalDate.now().plusDays(10),
                        bookingRepository.nextIdentity(),
                        guestsExpected.get(2),
                        servicesExpected)
        );
        bookingsExpected.get(0).addRoomCategory(categoriesExpected.get(0), 1);
        bookingsExpected.get(1).addRoomCategory(categoriesExpected.get(1), 1);
        bookingsExpected.get(2).addRoomCategory(categoriesExpected.get(0),2);

        List<String> roomsNameExpected = Arrays.asList("Room1","Room2","Room3");
        RoomStatus roomStatusExpected = RoomStatus.FREE;

        List<Room> roomsExpected = List.of(
                Room.create(
                        roomsNameExpected.get(0),
                        roomStatusExpected,
                        categoriesExpected.get(0)),
                Room.create(
                        roomsNameExpected.get(1),
                        roomStatusExpected,
                        categoriesExpected.get(1)),
                Room.create(
                        roomsNameExpected.get(2),
                        roomStatusExpected,
                        categoriesExpected.get(1))
        );

        List<Stay> staysExpected = List.of(
                Stay.create(bookingsExpected.get(0),roomsExpected),
                Stay.create(bookingsExpected.get(1),roomsExpected),
                Stay.create(bookingsExpected.get(2),roomsExpected)
        );
        List<InvoiceId> invoiceIdsExpected = List.of(
                new InvoiceId("1337"),
                new InvoiceId("1338"),
                new InvoiceId("1339")
        );
        BigDecimal amountExpected = new BigDecimal(1000);
        List<Invoice> invoicesExpected = invoiceIdsExpected.stream()
                .map(id -> Invoice.create(id,staysExpected.listIterator().next(),amountExpected))
                .collect(Collectors.toList());
        //when
        guestsExpected.forEach(guest -> this.guestRepository.add(guest));
        categoriesExpected.forEach(category -> this.roomCategoryRepository.add(category));
        servicesExpected.forEach(service -> this.serviceRepository.add(service));
        bookingsExpected.forEach(booking -> this.bookingRepository.add(booking));
        roomsExpected.forEach(room -> this.roomRepository.add(room));
        staysExpected.forEach(stay -> this.stayRepository.add(stay));
        invoicesExpected.forEach(invoice -> this.invoiceRepository.add(invoice));
        em.flush();
        List<Invoice> invoicesActual = this.invoiceRepository.findAllInvoices();

        //then
        assertEquals(invoicesExpected.size(), invoicesActual.size());
        for (Invoice i : invoicesActual) {
            assertTrue(invoicesExpected.contains(i));
        }
    }
}
