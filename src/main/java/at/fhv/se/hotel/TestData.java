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
import java.sql.Array;
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
                new RoomCategoryName("Single room"),
                new Description("Single room")
        );
        this.roomCategoryRepository.add(singleRoom);

        RoomCategory doubleRoom = RoomCategory.create(roomCategoryRepository.nextIdentity(),
                new RoomCategoryName("Double room"),
                new Description("Double room")
        );
        this.roomCategoryRepository.add(doubleRoom);

        RoomCategory luxuryRoom = RoomCategory.create(roomCategoryRepository.nextIdentity(),
                new RoomCategoryName("Luxury room"),
                new Description("Luxury room")
        );
        this.roomCategoryRepository.add(luxuryRoom);



/*----- Seasons -----*/
        Season winterSeason1 = Season.create(
                seasonRepository.nextIdentity(),
                new SeasonName("Winter 2021/2022"),
                LocalDate.of(2021, 12, 1),
                LocalDate.of(2022, 1, 31)
        );
        seasonRepository.add(winterSeason1);

        Season springSeason1 = Season.create(
                seasonRepository.nextIdentity(),
                new SeasonName("Spring 2022"),
                LocalDate.of(2022, 2, 1),
                LocalDate.of(2022, 5, 31)
        );
        seasonRepository.add(springSeason1);

        Season summerSeason1 = Season.create(
                seasonRepository.nextIdentity(),
                new SeasonName("Summer 2022"),
                LocalDate.of(2022, 6, 1),
                LocalDate.of(2022, 11, 30)
        );
        seasonRepository.add(summerSeason1);

        Season winterSeason2 = Season.create(
                seasonRepository.nextIdentity(),
                new SeasonName("Winter 2022/2023"),
                LocalDate.of(2022, 12, 1),
                LocalDate.of(2023, 1, 31)
        );
        seasonRepository.add(winterSeason2);

        Season springSeason2 = Season.create(
                seasonRepository.nextIdentity(),
                new SeasonName("Spring 2023"),
                LocalDate.of(2023, 2, 1),
                LocalDate.of(2023, 5, 31)
        );
        seasonRepository.add(springSeason2);

        Season summerSeason2 = Season.create(
                seasonRepository.nextIdentity(),
                new SeasonName("Summer 2023"),
                LocalDate.of(2023, 6, 1),
                LocalDate.of(2023, 11, 30)
        );
        seasonRepository.add(summerSeason2);



/*----- Category Prices -----*/
    // single
        RoomCategoryPrice singleRoomWinterPrice1 = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                winterSeason1,
                singleRoom,
                new BigDecimal("50")
        );

        RoomCategoryPrice singleRoomSpringPrice1 = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                springSeason1,
                singleRoom,
                new BigDecimal("30")
        );

        RoomCategoryPrice singleRoomSummerPrice1 = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                summerSeason1,
                singleRoom,
                new BigDecimal("40")
        );

        this.roomCategoryPriceRepository.add(singleRoomWinterPrice1);
        this.roomCategoryPriceRepository.add(singleRoomSpringPrice1);
        this.roomCategoryPriceRepository.add(singleRoomSummerPrice1);

    // double
        RoomCategoryPrice doubleRoomWinterPrice1 = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                winterSeason1,
                doubleRoom,
                new BigDecimal("90")
        );

        RoomCategoryPrice doubleRoomSpringPrice1 = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                springSeason1,
                doubleRoom,
                new BigDecimal("60")
        );

        RoomCategoryPrice doubleRoomSummerPrice1 = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                summerSeason1,
                doubleRoom,
                new BigDecimal("80")
        );

        this.roomCategoryPriceRepository.add(doubleRoomWinterPrice1);
        this.roomCategoryPriceRepository.add(doubleRoomSpringPrice1);
        this.roomCategoryPriceRepository.add(doubleRoomSummerPrice1);


    // luxury
        RoomCategoryPrice luxuryRoomWinterPrice1 = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                winterSeason1,
                luxuryRoom,
                new BigDecimal("250")
        );

        RoomCategoryPrice luxuryRoomSpringPrice1 = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                springSeason1,
                luxuryRoom,
                new BigDecimal("175")
        );

        RoomCategoryPrice luxuryRoomSummerPrice1 = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                summerSeason1,
                luxuryRoom,
                new BigDecimal("225")
        );

        this.roomCategoryPriceRepository.add(luxuryRoomWinterPrice1);
        this.roomCategoryPriceRepository.add(luxuryRoomSpringPrice1);
        this.roomCategoryPriceRepository.add(luxuryRoomSummerPrice1);



/*----- Rooms -----*/
        Room roomS0 = Room.create(new RoomName("S100"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS0);

        Room roomS1 = Room.create(new RoomName("S101"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS1);

        Room roomS2 = Room.create(new RoomName("S102"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS2);

        Room roomS3 = Room.create(new RoomName("S103"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS3);

        Room roomS4 = Room.create(new RoomName("S104"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS4);

        Room roomS5 = Room.create(new RoomName("S105"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS5);

        Room roomS6 = Room.create(new RoomName("S106"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS6);

        Room roomS7 = Room.create(new RoomName("S107"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS7);

        Room roomS8 = Room.create(new RoomName("S108"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS8);

        Room roomS9 = Room.create(new RoomName("S109"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS9);


        Room roomD0 = Room.create(new RoomName("D100"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD0);

        Room roomD1 = Room.create(new RoomName("D101"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD1);

        Room roomD2 = Room.create(new RoomName("D102"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD2);

        Room roomD3 = Room.create(new RoomName("D103"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD3);

        Room roomD4 = Room.create(new RoomName("D104"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD4);

        Room roomD5 = Room.create(new RoomName("D105"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD5);

        Room roomD6 = Room.create(new RoomName("D106"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD6);

        Room roomD7 = Room.create(new RoomName("D107"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD7);

        Room roomD8 = Room.create(new RoomName("D108"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD8);

        Room roomD9 = Room.create(new RoomName("D109"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD9);


        Room roomL0 = Room.create(new RoomName("L200"), RoomStatus.FREE, luxuryRoom);
        this.roomRepository.add(roomL0);

        Room roomL1 = Room.create(new RoomName("L201"), RoomStatus.FREE, luxuryRoom);
        this.roomRepository.add(roomL1);

        Room roomL2 = Room.create(new RoomName("L202"), RoomStatus.FREE, luxuryRoom);
        this.roomRepository.add(roomL2);

        Room roomL3 = Room.create(new RoomName("L203"), RoomStatus.FREE, luxuryRoom);
        this.roomRepository.add(roomL3);

        Room roomL4 = Room.create(new RoomName("L204"), RoomStatus.FREE, luxuryRoom);
        this.roomRepository.add(roomL4);




/*----- Services -----*/
        for (int i = 0; i < 15; i++) {
            Service tv = Service.create(serviceRepository.nextIdentity(), new ServiceName("TV"), new Price(new BigDecimal("10")));
            this.serviceRepository.add(tv);
        }
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

        Service fitnessRoom = Service.create(serviceRepository.nextIdentity(), new ServiceName("Fitness Room"), new Price(new BigDecimal("8")));
        this.serviceRepository.add(fitnessRoom);

        Service newsPaper = Service.create(serviceRepository.nextIdentity(), new ServiceName("Newspaper"), new Price(new BigDecimal("2")));
        this.serviceRepository.add(newsPaper);

        Service parkingPlace = Service.create(serviceRepository.nextIdentity(), new ServiceName("Parking place"), new Price(new BigDecimal("10")));
        this.serviceRepository.add(parkingPlace);

        Service miniBar = Service.create(serviceRepository.nextIdentity(), new ServiceName("Minibar"), new Price(new BigDecimal("25")));
        this.serviceRepository.add(miniBar);



/*----- Guests -----*/
        for (int i = 0; i < 15; i++) {
            Guest ali = Guest.create(guestRepository.nextIdentity(),
                    new FullName("Ali", "Cinar"),
                    Gender.MALE,
                    new Address("Hauptplatz", "73", "Oberkulm", "4210", "Austria"),
                    LocalDate.of(1997, 8, 27),
                    "+43 680 7994750",
                    "ali.cinar@gmail.at",
                    0,
                    Collections.emptyList()
            );
            this.guestRepository.add(ali);
        }

        Guest ali = Guest.create(guestRepository.nextIdentity(),
                new FullName("Ali", "Cinar"),
                Gender.MALE,
                new Address("Hauptplatz", "73", "Oberkulm", "4210", "Austria"),
                LocalDate.of(1997, 8, 27),
                "+43 680 7994750",
                "ali.cinar@gmail.at",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(ali);

        Guest michael = Guest.create(guestRepository.nextIdentity(),
                new FullName("Michael", "Spiegel"),
                Gender.MALE,
                new Address("Polletstraße", "26", "Oberkulm", "1220", "Wien"),
                LocalDate.of(1995, 9, 24),
                "+43 650 7131945",
                "michael.spiegel@gmx.at",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(michael);

        Guest johannes = Guest.create(guestRepository.nextIdentity(),
                new FullName("Johannes", "Moosbrugger"),
                Gender.MALE,
                new Address("Birkenweg", "9" , "Radstadt", "5550", "Austria"),
                LocalDate.of(1999, 1, 1),
                "+43 660 2648080",
                "johannes.moosbrugger@gmx.at",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(johannes);

        Guest herbert = Guest.create(guestRepository.nextIdentity(),
                new FullName("Herbert", "Steurer"),
                Gender.MALE,
                new Address("Kielmanseggasse", "29" , "Radstadt", "2340", "Austria"),
                LocalDate.of(1979, 10, 1),
                "+43 660 2649080",
                "herbert.steurer@yahoo.at",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(herbert);

        Guest dario = Guest.create(guestRepository.nextIdentity(),
                new FullName("Dario", "Birbarmer"),
                Gender.MALE,
                new Address("Forststraße", "8", "Kirchbichl", "6322", "Austria"),
                LocalDate.of(1998, 8, 27),
                "+43 676 4679464",
                "dario.birbarmer@students.fhv.at",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(dario);

        Guest umut = Guest.create(guestRepository.nextIdentity(),
                new FullName("Umut", "Caglayan"),
                Gender.MALE,
                new Address("Maternaweg", "18", "Wien", "1160", "Austria"),
                LocalDate.of(1995, 6, 27),
                "+43 680 4589452",
                "umut.caglayan@students.fhv.at",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(umut);



/*----- Bookings -----*/
        Booking b1 = Booking.create(
                LocalDate.now().plusDays(0),
                LocalDate.now().plusDays(2),
                bookingRepository.nextIdentity(),
                johannes,
                List.of(tv,bike),
                1,
                0,
                ""
        );
        b1.addRoomCategory(singleRoom, 1);
        this.bookingRepository.add(b1);

        Stay stay1 = Stay.create(b1, Map.of(roomS1, false));
        b1.deactivate();
        roomS1.occupy();
        this.stayRepository.add(stay1);

        Booking b2 = Booking.create(
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                bookingRepository.nextIdentity(),
                ali,
                List.of(eBike),
                2,
                0,
                "Vegan"
        );
        b2.addRoomCategory(singleRoom, 2);
        b2.addRoomCategory(doubleRoom, 2);
        b2.addRoomCategory(luxuryRoom, 2);
        this.bookingRepository.add(b2);
    }
}
