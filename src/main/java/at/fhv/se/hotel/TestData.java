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
import at.fhv.se.hotel.domain.model.service.ServiceName;
import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
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

    @Autowired
    StayRepository stayRepository;

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

        Guest herbert = Guest.create(guestRepository.nextIdentity(),
            new FullName("Herbert", "Steurer"),
            Gender.MALE,
            new Address("Schulgasse", "9" , "Meiningen", "6812", "Austria"),
            LocalDate.of(1980, 3, 10),
            "+43 664 2540490",
            "herbert.steurer@gmx.at",
            0,
            Collections.emptyList()
        );
        this.guestRepository.add(herbert);

        Guest johannes = Guest.create(guestRepository.nextIdentity(),
                new FullName("Johannes", "Moosbrugger"),
                Gender.MALE,
                new Address("Schollenstraße", "6" , "Hohenems", "6845", "Austria"),
                LocalDate.of(1999, 1, 1),
                "+43 660 2648080",
                "johannes.moosbrugger@gmx.at",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(johannes);

        Guest nina = Guest.create(guestRepository.nextIdentity(),
                new FullName("Nina", "Mähr"),
                Gender.MALE,
                new Address("Hofsteigstraße", "68" , "Schwarzach", "6858", "Austria"),
                LocalDate.of(2000, 10, 1),
                "+43 660 6884520",
                "nina.maehr@gmx.at",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(nina);

        Guest michael = Guest.create(guestRepository.nextIdentity(),
                new FullName("Michael", "Spiegel"),
                Gender.MALE,
                new Address("Hochschulstraße",
                        "1", "Dornbirn",
                        "6850", "Austria"),
                LocalDate.of(1999, 3, 20),
                "+43 660 123 456 789",
                "michael.spiegel@students.fhv.at",
                10.0,
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
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(ali);

        Guest dario = Guest.create(guestRepository.nextIdentity(),
                new FullName("Dario", "Birbarmer"),
                Gender.MALE,
                new Address("Hochschulstraße",
                        "19", "Dornbirn",
                        "6850", "Austria"),
                LocalDate.of(1997, 8, 27),
                "+43 676 123 456 789",
                "ali.cinar@students.fhv.at",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(ali);

        Guest umut = Guest.create(guestRepository.nextIdentity(),
                new FullName("Umut", "Cinar"),
                Gender.MALE,
                new Address("Hochschulstraße",
                        "91", "Dornbirn",
                        "6850", "Austria"),
                LocalDate.of(1997, 8, 27),
                "+43 676 123 456 789",
                "ali.cinar@students.fhv.at",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(ali);


        {
            Booking booking = Booking.create(
                    LocalDate.now().plusDays(-1),
                    LocalDate.now().plusDays(1),
                    bookingRepository.nextIdentity(),
                    johannes,
                    List.of(tvService),
                    2,
                    0,
                    "Extra pillow"
            );
            booking.addRoomCategory(singleRoom, 1);
            this.bookingRepository.add(booking);
        }
        {
            Booking booking = Booking.create(
                    LocalDate.now().plusDays(0),
                    LocalDate.now().plusDays(2),
                    bookingRepository.nextIdentity(),
                    michael,
                    List.of(tvService),
                    2,
                    0,
                    "Extra pillow"
            );
            booking.addRoomCategory(singleRoom, 1);
            this.bookingRepository.add(booking);
        }
        {
            Booking booking = Booking.create(
                    LocalDate.now().plusDays(1),
                    LocalDate.now().plusDays(3),
                    bookingRepository.nextIdentity(),
                    nina,
                    List.of(tvService),
                    2,
                    0,
                    "Extra pillow"
            );
            booking.addRoomCategory(singleRoom, 1);
            this.bookingRepository.add(booking);
        }
        {
            Booking booking = Booking.create(
                    LocalDate.now().plusDays(7),
                    LocalDate.now().plusDays(9),
                    bookingRepository.nextIdentity(),
                    herbert,
                    List.of(tvService),
                    2,
                    0,
                    "Extra pillow"
            );
            booking.addRoomCategory(singleRoom, 1);
            this.bookingRepository.add(booking);
        }
        {
            Booking booking = Booking.create(
                    LocalDate.now().plusDays(8),
                    LocalDate.now().plusDays(10),
                    bookingRepository.nextIdentity(),
                    johannes,
                    List.of(tvService),
                    2,
                    0,
                    "Extra pillow"
            );
            booking.addRoomCategory(singleRoom, 1);
            this.bookingRepository.add(booking);
        }
        {
            Booking booking = Booking.create(
                    LocalDate.now().plusDays(9),
                    LocalDate.now().plusDays(11),
                    bookingRepository.nextIdentity(),
                    michael,
                    List.of(tvService),
                    2,
                    0,
                    "Extra pillow"
            );
            booking.addRoomCategory(singleRoom, 1);
            this.bookingRepository.add(booking);
        }
        {
            Booking booking = Booking.create(
                    LocalDate.now().plusDays(10),
                    LocalDate.now().plusDays(12),
                    bookingRepository.nextIdentity(),
                    umut,
                    List.of(tvService),
                    2,
                    0,
                    "Extra pillow"
            );
            booking.addRoomCategory(singleRoom, 1);
            this.bookingRepository.add(booking);
        }
        {
            Booking booking = Booking.create(
                    LocalDate.now().plusDays(11),
                    LocalDate.now().plusDays(13),
                    bookingRepository.nextIdentity(),
                    ali,
                    List.of(tvService),
                    2,
                    0,
                    "Extra pillow"
            );
            booking.addRoomCategory(singleRoom, 1);
            this.bookingRepository.add(booking);
        }
        {
            Booking booking = Booking.create(
                    LocalDate.now().plusDays(11),
                    LocalDate.now().plusDays(13),
                    bookingRepository.nextIdentity(),
                    dario,
                    List.of(tvService),
                    2,
                    0,
                    "Extra pillow"
            );
            booking.addRoomCategory(singleRoom, 1);
            this.bookingRepository.add(booking);
        }
        {
            Booking booking = Booking.create(
                    LocalDate.now().plusDays(11),
                    LocalDate.now().plusDays(13),
                    bookingRepository.nextIdentity(),
                    umut,
                    List.of(tvService),
                    2,
                    0,
                    "Extra pillow"
            );
            booking.addRoomCategory(singleRoom, 1);
            this.bookingRepository.add(booking);
        }
        {
            Booking booking = Booking.create(
                    LocalDate.now().plusDays(11),
                    LocalDate.now().plusDays(13),
                    bookingRepository.nextIdentity(),
                    michael,
                    List.of(tvService),
                    2,
                    0,
                    "Extra pillow"
            );
            booking.addRoomCategory(singleRoom, 1);
            this.bookingRepository.add(booking);
        }

        Booking booking1 = Booking.create(
                LocalDate.now(),
                LocalDate.now().plusDays(8),
                bookingRepository.nextIdentity(),
                michael,
                List.of(tvService),
                2,
                0,
                "Extra pillow"
        );

        booking1.addRoomCategory(singleRoom, 1);
        this.bookingRepository.add(booking1);

        Booking booking2 = Booking.create(
                LocalDate.now().plusDays(35),
                LocalDate.now().plusDays(40),
                bookingRepository.nextIdentity(),
                ali,
                List.of(tvService, breakfastService),
                2,
                0,
                "Vegetarian"
        );
        booking2.addRoomCategory(singleRoom, 2);
        booking2.addRoomCategory(doubleRoom, 1);
        this.bookingRepository.add(booking2);

        // Insert fake rooms

        for(int i = 0; i < 10; i += 1) {

            Booking booking = Booking.create(
                    LocalDate.now().plusDays(11),
                    LocalDate.now().plusDays(13),
                    bookingRepository.nextIdentity(),
                    michael,
                    List.of(tvService),
                    2,
                    0,
                    "Extra pillow"
            );
            booking.addRoomCategory(singleRoom, 1);
            this.bookingRepository.add(booking);

            Room room1 = Room.create(Integer.toString(i), RoomStatus.FREE, singleRoom);
            this.roomRepository.add(room1);

            Stay stay1 = Stay.create(booking, List.of(room1));
            booking.deactivate();
            this.stayRepository.add(stay1);

        }

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

        // Insert Fake Stays
        Stay stay1 = Stay.create(booking1, List.of(room1));
        booking1.deactivate();
        this.stayRepository.add(stay1);
    }
}
