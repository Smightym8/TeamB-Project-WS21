package at.fhv.se.hotel;

import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.guest.Address;
import at.fhv.se.hotel.domain.model.guest.FullName;
import at.fhv.se.hotel.domain.model.guest.Gender;
import at.fhv.se.hotel.domain.model.guest.Guest;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomName;
import at.fhv.se.hotel.domain.model.room.RoomStatus;
import at.fhv.se.hotel.domain.model.roomcategory.*;
import at.fhv.se.hotel.domain.model.season.Season;
import at.fhv.se.hotel.domain.model.season.SeasonName;
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
import java.util.Map;

@Profile("dev")
@Component
@Transactional
public class TestData implements ApplicationRunner {
    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    RoomCategoryRepository roomCategoryRepository;

    @Autowired
    SeasonRepository seasonRepository;

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

        /*----- Room Categories -----*/
        RoomCategory singleRoom = RoomCategory.create(roomCategoryRepository.nextIdentity(),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );
        this.roomCategoryRepository.add(singleRoom);


        RoomCategory doubleRoom = RoomCategory.create(roomCategoryRepository.nextIdentity(),
                new RoomCategoryName("Double Room"),
                new Description("This is a double room")
        );
        this.roomCategoryRepository.add(doubleRoom);


        RoomCategory luxuryRoom = RoomCategory.create(roomCategoryRepository.nextIdentity(),
                new RoomCategoryName("Luxury Room"),
                new Description("This is a single room")
        );
        this.roomCategoryRepository.add(luxuryRoom);



        /*----- Rooms -----*/
        Room roomS0 = Room.create("S100", RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS0);

        Room roomS1 = Room.create("S101", RoomStatus.CLEANING, singleRoom);
        this.roomRepository.add(roomS1);

        Room roomS2 = Room.create("S102", RoomStatus.OCCUPIED, singleRoom);
        this.roomRepository.add(roomS2);

        Room roomS3 = Room.create("S103", RoomStatus.UNAVAILABLE, singleRoom);
        this.roomRepository.add(roomS3);

        Room roomS4 = Room.create("S104", RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS4);

        Room roomS5 = Room.create("S105", RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS5);

        Room roomS6 = Room.create("S106", RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS6);

        Room roomS7 = Room.create("S107", RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS7);

        Room roomS8 = Room.create("S108", RoomStatus.UNAVAILABLE, singleRoom);
        this.roomRepository.add(roomS8);

        Room roomS9 = Room.create("S109", RoomStatus.UNAVAILABLE, singleRoom);
        this.roomRepository.add(roomS9);


        Room roomD0 = Room.create("D200", RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD0);

        Room roomD1 = Room.create("D201", RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD1);

        Room roomD2 = Room.create("D202", RoomStatus.OCCUPIED, doubleRoom);
        this.roomRepository.add(roomD2);

        Room roomD3 = Room.create("D203", RoomStatus.OCCUPIED, doubleRoom);
        this.roomRepository.add(roomD3);

        Room roomD4 = Room.create("D204", RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD4);

        Room roomD5 = Room.create("D205", RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD5);

        Room roomD6 = Room.create("D206", RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD6);

        Room roomD7 = Room.create("D207", RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD7);

        Room roomD8 = Room.create("D208", RoomStatus.UNAVAILABLE, doubleRoom);
        this.roomRepository.add(roomD8);

        Room roomD9 = Room.create("D209", RoomStatus.UNAVAILABLE, doubleRoom);
        this.roomRepository.add(roomD9);


        Room roomL0 = Room.create("L300", RoomStatus.FREE, luxuryRoom);
        this.roomRepository.add(roomL0);

        Room roomL1 = Room.create("L301", RoomStatus.FREE, luxuryRoom);
        this.roomRepository.add(roomL1);

        Room roomL2 = Room.create("L302", RoomStatus.OCCUPIED, luxuryRoom);
        this.roomRepository.add(roomL2);

        Room roomL3 = Room.create("L303", RoomStatus.OCCUPIED, luxuryRoom);
        this.roomRepository.add(roomL3);

        Room roomL4 = Room.create("L304", RoomStatus.FREE, luxuryRoom);
        this.roomRepository.add(roomL4);

        Room roomL5 = Room.create("L305", RoomStatus.FREE, luxuryRoom);
        this.roomRepository.add(roomL5);

        Room roomL6 = Room.create("L306", RoomStatus.FREE, luxuryRoom);
        this.roomRepository.add(roomL6);

        Room roomL7 = Room.create("L307", RoomStatus.FREE, luxuryRoom);
        this.roomRepository.add(roomL7);

        Room roomL8 = Room.create("L308", RoomStatus.UNAVAILABLE, luxuryRoom);
        this.roomRepository.add(roomL8);

        Room roomL9 = Room.create("L309", RoomStatus.UNAVAILABLE, luxuryRoom);
        this.roomRepository.add(roomL9);



        /*----- Services -----*/
        Service tv = Service.create(serviceRepository.nextIdentity(), new ServiceName("TV"), new Price(new BigDecimal("10")));
        this.serviceRepository.add(tv);

        Service wlan = Service.create(serviceRepository.nextIdentity(), new ServiceName("WLAN"), new Price(new BigDecimal("5")));
        this.serviceRepository.add(wlan);

        Service swimmingPool = Service.create(serviceRepository.nextIdentity(), new ServiceName("Swimming pool"), new Price(new BigDecimal("12")));
        this.serviceRepository.add(swimmingPool);

        Service sauna = Service.create(serviceRepository.nextIdentity(), new ServiceName("Sauna"), new Price(new BigDecimal("7")));
        this.serviceRepository.add(sauna);

        Service bike = Service.create(serviceRepository.nextIdentity(), new ServiceName("Bike"), new Price(new BigDecimal("6")));
        this.serviceRepository.add(bike);

        Service eBike = Service.create(serviceRepository.nextIdentity(), new ServiceName("E-Bike"), new Price(new BigDecimal("10")));
        this.serviceRepository.add(eBike);

        Service fitenssRoom = Service.create(serviceRepository.nextIdentity(), new ServiceName("Fitness Room"), new Price(new BigDecimal("8")));
        this.serviceRepository.add(fitenssRoom);

        Service newsPaper = Service.create(serviceRepository.nextIdentity(), new ServiceName("Newspaper"), new Price(new BigDecimal("2")));
        this.serviceRepository.add(newsPaper);

        Service parkingPlace = Service.create(serviceRepository.nextIdentity(), new ServiceName("Parking place"), new Price(new BigDecimal("10")));
        this.serviceRepository.add(parkingPlace);

        Service miniBar = Service.create(serviceRepository.nextIdentity(), new ServiceName("Minibar"), new Price(new BigDecimal("25")));
        this.serviceRepository.add(miniBar);



        /*----- Services -----*/





        // Insert fake categories




        RoomCategoryPrice singleRoomSummerPrice = RoomCategoryPrice.create(
        // Insert fake seasons
        Season winterSeason1 = Season.create(
                seasonRepository.nextIdentity(),
                new SeasonName("Winter 2021/2022"),
                LocalDate.of(2021, 12, 1),
                LocalDate.of(2022, 1, 31)
        );

        Season springSeason1 = Season.create(
                seasonRepository.nextIdentity(),
                new SeasonName("Spring 2022"),
                LocalDate.of(2022, 2, 1),
                LocalDate.of(2022, 5, 31)
        );

        Season summerSeason1 = Season.create(
                seasonRepository.nextIdentity(),
                new SeasonName("Summer 2022"),
                LocalDate.of(2022, 6, 1),
                LocalDate.of(2022, 11, 30)
        );

        Season winterSeason2 = Season.create(
                seasonRepository.nextIdentity(),
                new SeasonName("Winter 2022/2023"),
                LocalDate.of(2022, 12, 1),
                LocalDate.of(2023, 1, 31)
        );

        Season springSeason2 = Season.create(
                seasonRepository.nextIdentity(),
                new SeasonName("Spring 2023"),
                LocalDate.of(2023, 2, 1),
                LocalDate.of(2023, 5, 31)
        );

        Season summerSeason2 = Season.create(
                seasonRepository.nextIdentity(),
                new SeasonName("Summer 2023"),
                LocalDate.of(2023, 6, 1),
                LocalDate.of(2023, 11, 30)
        );

        Season winterSeason3 = Season.create(
                seasonRepository.nextIdentity(),
                new SeasonName("Winter 2023/2024"),
                LocalDate.of(2023, 12, 1),
                LocalDate.of(2024, 1, 31)
        );

        seasonRepository.add(winterSeason1);
        seasonRepository.add(springSeason1);
        seasonRepository.add(summerSeason1);
        seasonRepository.add(winterSeason2);
        seasonRepository.add(springSeason2);
        seasonRepository.add(summerSeason2);
        seasonRepository.add(winterSeason3);

        // Insert fake prices
        RoomCategoryPrice singleRoomWinterPrice1 = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                winterSeason1,
                singleRoom,
                new BigDecimal("300")
        );

        RoomCategoryPrice singleRoomSpringPrice1 = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                springSeason1,
                singleRoom,
                new BigDecimal("200")
        );

        RoomCategoryPrice singleRoomSummerPrice1 = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                summerSeason1,
                singleRoom,
                new BigDecimal("600")
        );

        RoomCategoryPrice singleRoomWinterPrice2 = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                winterSeason2,
                singleRoom,
                new BigDecimal("300")
        );

        RoomCategoryPrice singleRoomSpringPrice2 = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                springSeason2,
                singleRoom,
                new BigDecimal("200")
        );

        RoomCategoryPrice singleRoomSummerPrice2 = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                summerSeason2,
                singleRoom,
                new BigDecimal("600")
        );

        RoomCategoryPrice doubleRoomWinterPrice1 = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                winterSeason1,
                doubleRoom,
                new BigDecimal("500")
        );

        RoomCategoryPrice doubleRoomSpringPrice1 = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                springSeason1,
                doubleRoom,
                new BigDecimal("400")
        );

        RoomCategoryPrice doubleRoomSummerPrice1 = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                summerSeason1,
                doubleRoom,
                new BigDecimal("900")
        );

        RoomCategoryPrice doubleRoomWinterPrice2 = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                winterSeason2,
                doubleRoom,
                new BigDecimal("500")
        );

        RoomCategoryPrice doubleRoomSpringPrice2 = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                springSeason2,
                doubleRoom,
                new BigDecimal("400")
        );

        RoomCategoryPrice doubleRoomSummerPrice2 = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                summerSeason2,
                doubleRoom,
                new BigDecimal("900")
        );

        this.roomCategoryPriceRepository.add(singleRoomWinterPrice1);
        this.roomCategoryPriceRepository.add(singleRoomSpringPrice1);
        this.roomCategoryPriceRepository.add(singleRoomSummerPrice1);
        this.roomCategoryPriceRepository.add(singleRoomWinterPrice2);
        this.roomCategoryPriceRepository.add(singleRoomSpringPrice2);
        this.roomCategoryPriceRepository.add(singleRoomSummerPrice2);

        this.roomCategoryPriceRepository.add(doubleRoomWinterPrice1);
        this.roomCategoryPriceRepository.add(doubleRoomSpringPrice1);
        this.roomCategoryPriceRepository.add(doubleRoomSummerPrice1);
        this.roomCategoryPriceRepository.add(doubleRoomWinterPrice2);
        this.roomCategoryPriceRepository.add(doubleRoomSpringPrice2);
        this.roomCategoryPriceRepository.add(doubleRoomSummerPrice2);

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
                new FullName("Umut", "Caglayan"),
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
                    List.of(tv,bike),
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
                    List.of(wlan),
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
                    List.of(wlan),
                    2,
                    0,
                    "Extra pillow"
            );
            booking.addRoomCategory(singleRoom, 1);
            this.bookingRepository.add(booking);
        }


        for (int i = 0; i < 15; i++) {


            Booking booking = Booking.create(
                    LocalDate.now().plusDays(11),
                    LocalDate.now().plusDays(13),
                    bookingRepository.nextIdentity(),
                    michael,
                    List.of(sauna, eBike, fitenssRoom),
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
                List.of(wlan, newsPaper),
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
                List.of(wlan, tv),
                2,
                0,
                "Vegetarian"
        );
        booking2.addRoomCategory(singleRoom, 2);
        booking2.addRoomCategory(doubleRoom, 1);
        this.bookingRepository.add(booking2);

        // Insert fake rooms

//        for(int i = 100; i < 110; i += 1) {
//
//            Booking booking = Booking.create(
//                    LocalDate.now().plusDays(11),
//                    LocalDate.now().plusDays(13),
//                    bookingRepository.nextIdentity(),
//                    michael,
//                    List.of(tvService),
//                    2,
//                    0,
//                    "Extra pillow"
//            );
//            booking.addRoomCategory(singleRoom, 1);
//            this.bookingRepository.add(booking);
//
//            Room room1 = Room.create("S" + Integer.toString(i), RoomStatus.FREE, singleRoom);
//            this.roomRepository.add(room1);
//
//            Stay stay1 = Stay.create(booking, List.of(room1));
//            booking.deactivate();
//            this.stayRepository.add(stay1);
//
//        }

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



        // Insert Fake Stays
        Stay stay1 = Stay.create(booking1, Map.of(roomS1, false));
        booking1.deactivate();
        this.stayRepository.add(stay1);
    }
}
