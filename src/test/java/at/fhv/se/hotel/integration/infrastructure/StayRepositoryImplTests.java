package at.fhv.se.hotel.integration.infrastructure;

import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;
import at.fhv.se.hotel.domain.model.guest.*;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomStatus;
import at.fhv.se.hotel.domain.model.roomcategory.Description;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryName;
import at.fhv.se.hotel.domain.model.service.Price;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.service.ServiceId;
import at.fhv.se.hotel.domain.model.service.ServiceName;
import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class StayRepositoryImplTests {
    @Autowired
    StayRepository stayRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    GuestRepository guestRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    RoomCategoryRepository roomCategoryRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    private EntityManager em;

    @AfterEach
    void cleanDatabase() {
        // TODO: Clear Database
        System.out.println("Clear database");
    }

    @Test
    void given_stay_when_addstayrepository_then_returnequalsstay() {
        // given
        Guest guestExpected = Guest.create(new GuestId("1"),
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

        List<RoomCategory> categoriesExpected = Arrays.asList(
                RoomCategory.create(new RoomCategoryId("1"),
                        new RoomCategoryName("Single Room"),
                        new Description("This is a single room")),
                RoomCategory.create(new RoomCategoryId("2"),
                        new RoomCategoryName("Double Room"),
                        new Description("This is a double room"))
        );

        List<Service> servicesExpected = Arrays.asList(
                Service.create(new ServiceId("1"),
                        new ServiceName("TV"),
                        new Price(new BigDecimal("100"))),
                Service.create(new ServiceId("2"),
                        new ServiceName("Breakfast"),
                        new Price(new BigDecimal("100")))
        );

        Booking bookingExpected = Booking.create(
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                new BookingId("1"),
                guestExpected,
                servicesExpected,
                2,
                1,
                "Nothing"
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

        // when
        this.guestRepository.add(guestExpected);
        this.roomCategoryRepository.add(categoriesExpected.get(0));
        this.roomCategoryRepository.add(categoriesExpected.get(1));
        this.serviceRepository.add(servicesExpected.get(0));
        this.serviceRepository.add(servicesExpected.get(1));
        this.bookingRepository.add(bookingExpected);
        this.roomRepository.add(roomsExpected.get(0));
        this.stayRepository.add(stayExpected);
        em.flush();

        Stay stayActual = this.stayRepository.stayById(stayExpected.getStayId()).get();

        // then
        assertEquals(stayExpected, stayActual);
    }

    @Test
    void given_stay_when_fetchstaybycheckout_then_returnequalsstay() {
        // given
        Guest guestExpected = Guest.create(new GuestId("1"),
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

        List<RoomCategory> categoriesExpected = Arrays.asList(
                RoomCategory.create(new RoomCategoryId("1"),
                        new RoomCategoryName("Single Room"),
                        new Description("This is a single room")),
                RoomCategory.create(new RoomCategoryId("2"),
                        new RoomCategoryName("Double Room"),
                        new Description("This is a double room"))
        );

        List<Service> servicesExpected = Arrays.asList(
                Service.create(new ServiceId("1"),
                        new ServiceName("TV"),
                        new Price(new BigDecimal("100"))),
                Service.create(new ServiceId("2"),
                        new ServiceName("Breakfast"),
                        new Price(new BigDecimal("100")))
        );

        LocalDate checkInDateExpected = LocalDate.of(2022, 5, 1);
        LocalDate checkOutDateExpected = LocalDate.of(2022, 5, 10);
        Booking bookingExpected = Booking.create(
                checkInDateExpected,
                checkOutDateExpected,
                new BookingId("1"),
                guestExpected,
                servicesExpected,
                2,
                1,
                "Nothing"
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

        // when
        this.guestRepository.add(guestExpected);
        this.roomCategoryRepository.add(categoriesExpected.get(0));
        this.roomCategoryRepository.add(categoriesExpected.get(1));
        this.serviceRepository.add(servicesExpected.get(0));
        this.serviceRepository.add(servicesExpected.get(1));
        this.bookingRepository.add(bookingExpected);
        this.roomRepository.add(roomsExpected.get(0));
        this.stayRepository.add(stayExpected);
        this.em.flush();

        List<Stay> staysActual = this.stayRepository.stayByCheckout(LocalDate.of(2022, 5, 10));
        Stay stayActual = staysActual.get(0); // Inserted only one stay

        // then
        assertEquals(stayExpected, stayActual);
        assertEquals(stayExpected.getStayId(), stayActual.getStayId());
    }

    @Test
    void given_3stays_when_fetchallstays_then_returnequalstays() {
        // given
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
                        Collections.emptyList()
                ),
                Guest.create(new GuestId("3"),
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
                RoomCategory.create(new RoomCategoryId("1"),
                        new RoomCategoryName("Single Room"),
                        new Description("This is a single room")),
                RoomCategory.create(new RoomCategoryId("2"),
                        new RoomCategoryName("Double Room"),
                        new Description("This is a double room"))
        );

        List<Service> servicesExpected = Arrays.asList(
                Service.create(new ServiceId("1"),
                        new ServiceName("TV"),
                        new Price(new BigDecimal("100"))),
                Service.create(new ServiceId("2"),
                        new ServiceName("Breakfast"),
                        new Price(new BigDecimal("100")))
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
                        "Nothing"),
                Booking.create(
                        LocalDate.now(),
                        LocalDate.now().plusDays(10),
                        new BookingId("2"),
                        guestsExpected.get(1),
                        servicesExpected,
                        2,
                        1,
                        "Nothing"),
                Booking.create(
                        LocalDate.now(),
                        LocalDate.now().plusDays(10),
                        new BookingId("3"),
                        guestsExpected.get(2),
                        servicesExpected,
                        2,
                        1,
                        "Nothing")
        );

        bookingsExpected.get(0).addRoomCategory(categoriesExpected.get(0), 1);
        bookingsExpected.get(1).addRoomCategory(categoriesExpected.get(1), 1);
        bookingsExpected.get(2).addRoomCategory(categoriesExpected.get(0),2);

        List<Room> rooms1 = List.of(
                Room.create(
                        "Room1",
                        RoomStatus.FREE,
                        categoriesExpected.get(0))
        );

        List<Room> rooms2 = List.of(
                Room.create(
                        "Room2",
                        RoomStatus.FREE,
                        categoriesExpected.get(1))
        );

        List<Room> rooms3 = List.of(
                Room.create(
                        "Room3",
                        RoomStatus.FREE,
                        categoriesExpected.get(0)),
                Room.create(
                        "Room4",
                        RoomStatus.FREE,
                        categoriesExpected.get(0))
        );

        List<Stay> staysExpected = List.of(
                Stay.create(bookingsExpected.get(0), rooms1),
                Stay.create(bookingsExpected.get(1), rooms2),
                Stay.create(bookingsExpected.get(2), rooms3)
        );

        // when
        guestsExpected.forEach(guest -> this.guestRepository.add(guest));
        categoriesExpected.forEach(category -> this.roomCategoryRepository.add(category));
        servicesExpected.forEach(service -> this.serviceRepository.add(service));
        bookingsExpected.forEach(booking -> this.bookingRepository.add(booking));
        rooms1.forEach(room -> this.roomRepository.add(room));
        rooms2.forEach(room -> this.roomRepository.add(room));
        rooms3.forEach(room -> this.roomRepository.add(room));
        staysExpected.forEach(stay -> this.stayRepository.add(stay));
        this.em.flush();

        List<Stay> staysActual = this.stayRepository.findAllStays();

        // then
        assertEquals(staysExpected.size(), staysActual.size());

        for(Stay s : staysActual) {
            assertTrue(staysExpected.contains(s));
        }
    }
}
