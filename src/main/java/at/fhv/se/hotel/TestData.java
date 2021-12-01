package at.fhv.se.hotel;

import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.guest.Address;
import at.fhv.se.hotel.domain.model.guest.FullName;
import at.fhv.se.hotel.domain.model.guest.Gender;
import at.fhv.se.hotel.domain.model.guest.Guest;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomStatus;
import at.fhv.se.hotel.domain.model.roomcategory.*;
import at.fhv.se.hotel.domain.model.service.Price;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.service.ServiceId;
import at.fhv.se.hotel.domain.model.service.ServiceName;
import at.fhv.se.hotel.domain.repository.*;
import at.fhv.se.hotel.infrastructure.HibernateServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Profile("!test")
@Component
@Transactional
public class TestData implements ApplicationRunner {
    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    RoomCategoryRepository roomCategoryRepository;

    @Autowired
    GuestRepository guestRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    RoomCategoryPriceRepository roomCategoryPriceRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Insert fake services
        Service tvService = Service.create(serviceRepository.nextIdentity(), new ServiceName("TV"), new Price(new BigDecimal("100")));
        Service breakfastService = Service.create(serviceRepository.nextIdentity(), new ServiceName("Breakfast"), new Price(new BigDecimal("100")));

        this.serviceRepository.add(tvService);
        this.serviceRepository.add(breakfastService);

        // Insert fake categories
        RoomCategory singleRoom = RoomCategory.create(roomCategoryRepository.nextIdentity(),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        RoomCategory doubleRoom = RoomCategory.create(roomCategoryRepository.nextIdentity(),
                new RoomCategoryName("Double Room"),
                new Description("This is a double room")
        );

        this.roomCategoryRepository.add(singleRoom);
        this.roomCategoryRepository.add(doubleRoom);

        RoomCategoryPrice singleRoomSummerPrice = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                Season.SUMMER,
                singleRoom,
                new BigDecimal("600")
        );

        RoomCategoryPrice singleRoomWinterPrice = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                Season.WINTER,
                singleRoom,
                new BigDecimal("300")
        );

        RoomCategoryPrice singleRoomSpringPrice = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                Season.SPRING,
                singleRoom,
                new BigDecimal("200")
        );

        RoomCategoryPrice doubleRoomSummerPrice = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                Season.SUMMER,
                doubleRoom,
                new BigDecimal("900")
        );

        RoomCategoryPrice doubleRoomWinterPrice = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                Season.WINTER,
                doubleRoom,
                new BigDecimal("500")
        );

        RoomCategoryPrice doubleRoomSpringPrice = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                Season.SPRING,
                doubleRoom,
                new BigDecimal("400")
        );


        this.roomCategoryPriceRepository.add(singleRoomWinterPrice);
        this.roomCategoryPriceRepository.add(singleRoomSpringPrice);
        this.roomCategoryPriceRepository.add(singleRoomSummerPrice);
        this.roomCategoryPriceRepository.add(doubleRoomWinterPrice);
        this.roomCategoryPriceRepository.add(doubleRoomSpringPrice);
        this.roomCategoryPriceRepository.add(doubleRoomSummerPrice);

        // Insert fake guests
        Guest michael = Guest.create(guestRepository.nextIdentity(),
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
        this.guestRepository.add(michael);

        Guest ali = Guest.create(guestRepository.nextIdentity(),
                new FullName("Ali", "Cinar"),
                Gender.MALE,
                new Address("Hochschulstraße",
                        "1", "Dornbirn",
                        "6850", "Austria"),
                LocalDate.of(1997, 8, 27),
                "+43 676 123 456 789",
                "ali.cinar@students.fhv.at",
                Collections.emptyList()
        );
        this.guestRepository.add(ali);

        // Insert fake bookings
        Booking booking1 = Booking.create(
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                bookingRepository.nextIdentity(),
                michael,
                List.of(tvService),
                2,
                0
        );

        booking1.addRoomCategory(singleRoom, 1);
        this.bookingRepository.add(booking1);

        Booking booking2 = Booking.create(
                LocalDate.now().plusDays(30),
                LocalDate.now().plusDays(40),
                bookingRepository.nextIdentity(),
                ali,
                List.of(tvService, breakfastService),
                2,
                0
        );
        booking2.addRoomCategory(singleRoom, 2);
        booking2.addRoomCategory(doubleRoom, 1);
        this.bookingRepository.add(booking2);

        // Insert fake rooms
        Room room1 = Room.create("101", RoomStatus.FREE, singleRoom);
        this.roomRepository.add(room1);

        Room room2 = Room.create("102", RoomStatus.OCCUPIED, singleRoom);
        this.roomRepository.add(room2);

        Room room3 = Room.create("103", RoomStatus.FREE, singleRoom);
        this.roomRepository.add(room3);

        Room room4 = Room.create("104", RoomStatus.FREE, singleRoom);
        this.roomRepository.add(room4);

        Room room5 = Room.create("105", RoomStatus.FREE, singleRoom);
        this.roomRepository.add(room5);

        Room room6 = Room.create("106", RoomStatus.FREE, singleRoom);
        this.roomRepository.add(room6);

        Room room7 = Room.create("107", RoomStatus.FREE, singleRoom);
        this.roomRepository.add(room7);

        Room room8 = Room.create("108", RoomStatus.FREE, singleRoom);
        this.roomRepository.add(room8);



        Room room10 = Room.create("201", RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(room10);

        Room room11 = Room.create("202", RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(room11);

        Room room12 = Room.create("203", RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(room12);

        Room room13 = Room.create("204", RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(room13);

        Room room14 = Room.create("205", RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(room14);

        Room room15 = Room.create("206", RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(room15);
    }
}
