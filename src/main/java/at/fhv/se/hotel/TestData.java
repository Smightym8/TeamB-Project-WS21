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
import java.time.format.DateTimeFormatter;
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
                new Description("You love spending the whole day in nature, exploring the mountain world extensively and are a fan of beautiful rooms with a casual stylish look, without wanting too much of a good thing. Our lovely double room Casual, with approx. 25 m², offers all the amenities that mountain enthusiasts like after a full day: comfortable box spring bed for a wonderful night's sleep, beautiful bathroom with large rain shower, mini-fridge (not filled), Nespresso machine, kettle, balcony and much more. The \"Casual\" can also be booked for single use.")
        );
        this.roomCategoryRepository.add(singleRoom);

        RoomCategory doubleRoom = RoomCategory.create(roomCategoryRepository.nextIdentity(),
                new RoomCategoryName("Double room"),
                new Description("In our double room \"Stylish\" you will be pampered with a king-size box-spring bed. You will sleep pleasantly quiet and fully relax on holiday - like a prince and princess. With approx. 30 m², the two of you have plenty of space. You will enjoy using the stylish sofa or the recliner to relax, while your wife occupies the bathroom for evening styling. The balcony with its magnificent view will tempt you out in the morning to enjoy a private cup of Nespresso coffee together.")
        );
        this.roomCategoryRepository.add(doubleRoom);

        RoomCategory juniorSuite = RoomCategory.create(roomCategoryRepository.nextIdentity(),
                new RoomCategoryName("Junior Suite"),
                new Description("In our junior suite \"Lifestyle\", we placed great emphasis on the balance between the living and sleeping areas. Spaciously divided, the sofa bed corner invites you to relax during the day. In the box spring bed you will wake up the next morning fully rested and relaxed. The bathroom with integrated infrared cabin, two washbasins, separately integrated WC and a large rain shower is already a great feel-good bathroom. Flat screen TV, Nespresso machine, mini-fridge (not filled), kettle and many other small details are part of the standard equipment.")
        );
        this.roomCategoryRepository.add(juniorSuite);

        RoomCategory suite = RoomCategory.create(roomCategoryRepository.nextIdentity(),
                new RoomCategoryName("Suite"),
                new Description("You will reside in our WOW suite as if in your own little flat. Step inside - you will be amazed. You will find everything you need for a perfect wellness holiday with lots of privacy and amenities that have been thought out down to the smallest detail. The separate living room with open fireplace is your personal retreat with a glass of good wine in the evening. In the bathroom you are welcome to use your own sauna and then cool off in the outdoor jacuzzi on the secluded terrace. If you have visitors, there is a guest toilet available.")
        );
        this.roomCategoryRepository.add(suite);



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
                juniorSuite,
                new BigDecimal("250")
        );

        RoomCategoryPrice luxuryRoomSpringPrice1 = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                springSeason1,
                juniorSuite,
                new BigDecimal("175")
        );

        RoomCategoryPrice luxuryRoomSummerPrice1 = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                summerSeason1,
                juniorSuite,
                new BigDecimal("225")
        );

        this.roomCategoryPriceRepository.add(luxuryRoomWinterPrice1);
        this.roomCategoryPriceRepository.add(luxuryRoomSpringPrice1);
        this.roomCategoryPriceRepository.add(luxuryRoomSummerPrice1);


        // suite
        RoomCategoryPrice suiteWinterPrice1 = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                winterSeason1,
                suite,
                new BigDecimal("350")
        );

        RoomCategoryPrice suiteSpringPrice1 = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                springSeason1,
                suite,
                new BigDecimal("275")
        );

        RoomCategoryPrice suiteSummerPrice1 = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                summerSeason1,
                suite,
                new BigDecimal("325")
        );

        this.roomCategoryPriceRepository.add(suiteWinterPrice1);
        this.roomCategoryPriceRepository.add(suiteSpringPrice1);
        this.roomCategoryPriceRepository.add(suiteSummerPrice1);



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

        Room roomS10 = Room.create(new RoomName("S110"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS10);

        Room roomS11 = Room.create(new RoomName("S111"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS11);

        Room roomS12 = Room.create(new RoomName("S112"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS12);

        Room roomS13 = Room.create(new RoomName("S113"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS13);

        Room roomS14 = Room.create(new RoomName("S114"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS14);

        Room roomS15 = Room.create(new RoomName("S115"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS15);

        Room roomS16 = Room.create(new RoomName("S116"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS16);

        Room roomS17 = Room.create(new RoomName("S117"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS17);

        Room roomS18 = Room.create(new RoomName("S118"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS18);

        Room roomS19 = Room.create(new RoomName("S119"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS19);


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

        Room roomD10 = Room.create(new RoomName("D100"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD10);

        Room roomD11 = Room.create(new RoomName("D111"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD11);

        Room roomD12 = Room.create(new RoomName("D112"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD12);

        Room roomD13 = Room.create(new RoomName("D113"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD13);

        Room roomD14 = Room.create(new RoomName("D114"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD14);

        Room roomD15 = Room.create(new RoomName("D115"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD15);

        Room roomD16 = Room.create(new RoomName("D116"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD16);

        Room roomD17 = Room.create(new RoomName("D117"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD17);

        Room roomD18 = Room.create(new RoomName("D118"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD18);

        Room roomD19 = Room.create(new RoomName("D119"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD19);


        Room roomL0 = Room.create(new RoomName("L200"), RoomStatus.FREE, juniorSuite);
        this.roomRepository.add(roomL0);

        Room roomL1 = Room.create(new RoomName("L201"), RoomStatus.FREE, juniorSuite);
        this.roomRepository.add(roomL1);

        Room roomL2 = Room.create(new RoomName("L202"), RoomStatus.FREE, juniorSuite);
        this.roomRepository.add(roomL2);

        Room roomL3 = Room.create(new RoomName("L203"), RoomStatus.FREE, juniorSuite);
        this.roomRepository.add(roomL3);

        Room roomL4 = Room.create(new RoomName("L204"), RoomStatus.FREE, juniorSuite);
        this.roomRepository.add(roomL4);

        Room roomL5 = Room.create(new RoomName("L205"), RoomStatus.FREE, juniorSuite);
        this.roomRepository.add(roomL5);


        Room roomSU0 = Room.create(new RoomName("SU300"), RoomStatus.FREE, suite);
        this.roomRepository.add(roomSU0);

        Room roomSU1 = Room.create(new RoomName("SU301"), RoomStatus.FREE, suite);
        this.roomRepository.add(roomSU1);

        Room roomSU2 = Room.create(new RoomName("SU302"), RoomStatus.FREE, suite);
        this.roomRepository.add(roomSU2);

        Room roomSU3 = Room.create(new RoomName("SU303"), RoomStatus.FREE, suite);
        this.roomRepository.add(roomSU3);


/*----- Services -----*/
        Service wlan = Service.create(serviceRepository.nextIdentity(), new ServiceName("WLAN"), new Price(new BigDecimal("5")));
        this.serviceRepository.add(wlan);

        Service swimmingPool = Service.create(serviceRepository.nextIdentity(), new ServiceName("Swimming pool"), new Price(new BigDecimal("12")));
        this.serviceRepository.add(swimmingPool);

        Service sauna = Service.create(serviceRepository.nextIdentity(), new ServiceName("Sauna"), new Price(new BigDecimal("7")));
        this.serviceRepository.add(sauna);

        Service fitnessRoom = Service.create(serviceRepository.nextIdentity(), new ServiceName("Fitness Room"), new Price(new BigDecimal("8")));
        this.serviceRepository.add(fitnessRoom);

        Service newsPaper = Service.create(serviceRepository.nextIdentity(), new ServiceName("Newspaper"), new Price(new BigDecimal("2")));
        this.serviceRepository.add(newsPaper);

        Service parkingPlace = Service.create(serviceRepository.nextIdentity(), new ServiceName("Parking place"), new Price(new BigDecimal("10")));
        this.serviceRepository.add(parkingPlace);

        Service miniBar = Service.create(serviceRepository.nextIdentity(), new ServiceName("Minibar"), new Price(new BigDecimal("25")));
        this.serviceRepository.add(miniBar);



        /*----- Guests -----*/
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
                new Address("Polletstraße", "26", "Oberkulm", "1220", "Austria"),
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
                LocalDate.of(1999, 7, 7),
                "+43 680 4589452",
                "umut.caglayan@students.fhv.at",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(umut);

        Guest billYard = Guest.create(guestRepository.nextIdentity(),
                new FullName("Bill", "Yard"),
                Gender.MALE,
                new Address("Stadionstrasse", "71", "Rastbach", "3542", "Austria"),
                LocalDate.of(1980, 5, 16),
                "+43 680 7569842",
                "bill.yard@students.fhv.at",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(billYard);

        Guest ernstHaft = Guest.create(guestRepository.nextIdentity(),
                new FullName("Ernst", "Haft"),
                Gender.MALE,
                new Address("Stubengraben", "47", "Ebersdorf", "8342", "Austria"),
                LocalDate.of(1975, 3, 20),
                "+43 680 3576523",
                "ernst.haft@students.fhv.at",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(ernstHaft);

        Guest klaraFall = Guest.create(guestRepository.nextIdentity(),
                new FullName("Klara", "Fall"),
                Gender.FEMALE,
                new Address("Pazmaniteng", "25", "Aham", "4800", "Austria"),
                LocalDate.of(1989, 9, 15),
                "+43 680 9534658",
                "klara.fall@students.fhv.at",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(klaraFall);

        Guest rainerZufall = Guest.create(guestRepository.nextIdentity(),
                new FullName("Rainer", "Zufall"),
                Gender.MALE,
                new Address("Floridusgasse", "96", "Wiederndorf", "9150", "Austria"),
                LocalDate.of(1985, 8, 3),
                "+43 680 1593698",
                "rainer.zufall@students.fhv.at",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(rainerZufall);

        Guest frankReich = Guest.create(guestRepository.nextIdentity(),
                new FullName("Frank", "Reich"),
                Gender.MALE,
                new Address("Salzburgerstrasse", "18", "Grossendorf", "4551", "Austria"),
                LocalDate.of(1970, 1, 1),
                "+43 680 4832679",
                "frank.reich@students.fhv.at",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(frankReich);

        Guest gerdNehr = Guest.create(guestRepository.nextIdentity(),
                new FullName("Gerd", "Nehr"),
                Gender.MALE,
                new Address("Mollardgasse", "36", "Teicht", "4870", "Austria"),
                LocalDate.of(1992, 3, 2),
                "+43 680 1530564",
                "gerd.nehr@students.fhv.at",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(gerdNehr);

        Guest klausTrophobie = Guest.create(guestRepository.nextIdentity(),
                new FullName("Klaus", "Trophobie"),
                Gender.MALE,
                new Address("Spanheimerstrasse", "7", "Pernegg", "8132", "Austria"),
                LocalDate.of(1996, 7, 30),
                "+43 680 7430138",
                "klaus.trophobie@students.fhv.at",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(klausTrophobie);

        Guest hellaWahnsinn = Guest.create(guestRepository.nextIdentity(),
                new FullName("Hella", "Wahnsinn"),
                Gender.MALE,
                new Address("Bonygasse", "99", "InnerhÖtzendorf", "4152", "Austria"),
                LocalDate.of(1975, 4, 24),
                "+43 680 1593640",
                "hella.wahnsinn@students.fhv.at",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(hellaWahnsinn);

        Guest kaiSer = Guest.create(guestRepository.nextIdentity(),
                new FullName("Kai", "Ser"),
                Gender.MALE,
                new Address("Holzstrasse", "61", "Sallingberg", "3525", "Austria"),
                LocalDate.of(1976, 5, 18),
                "+43 680 4327650",
                "kai.ser@students.fhv.at",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(kaiSer);

        Guest nickOlaus = Guest.create(guestRepository.nextIdentity(),
                new FullName("Nick", "Olaus"),
                Gender.MALE,
                new Address("Dieselstrasse", "54", "Latschach", "9582", "Austria"),
                LocalDate.of(1984, 12, 6),
                "+43 680 7618601",
                "nick.olaus@students.fhv.at",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(nickOlaus);

        Guest larsVegas = Guest.create(guestRepository.nextIdentity(),
                new FullName("Lars", "Vegas"),
                Gender.MALE,
                new Address("Thayapark", "72", "Rosenau Schloss", "3942", "Austria"),
                LocalDate.of(1997, 4, 3),
                "+43 680 1684301",
                "lars.vegas@students.fhv.at",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(larsVegas);

        Guest moniTor = Guest.create(guestRepository.nextIdentity(),
                new FullName("Moni", "Tor"),
                Gender.MALE,
                new Address("Grünbachstrasse", "18", "Oberassling", "9911", "Austria"),
                LocalDate.of(1995, 9, 20),
                "+43 680 6791560",
                "moni.tor@students.fhv.at",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(moniTor);

        Guest theoDorant = Guest.create(guestRepository.nextIdentity(),
                new FullName("Theo", "Dorant"),
                Gender.MALE,
                new Address("Kärntner Strasse", "14", "Haslau", "5261", "Austria"),
                LocalDate.of(1987, 4, 17),
                "+43 680 4310569",
                "theo.dorant@students.fhv.at",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(theoDorant);

        Guest wilmaBier = Guest.create(guestRepository.nextIdentity(),
                new FullName("Wilma", "Bier"),
                Gender.MALE,
                new Address("Hausergasse", "76", "Wuggitz", "8455", "Austria"),
                LocalDate.of(1976, 12, 24),
                "+43 680 4683570",
                "wilma.bier@students.fhv.at",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(wilmaBier);

        Guest ellenBogen = Guest.create(guestRepository.nextIdentity(),
                new FullName("Ellen", "Bogen"),
                Gender.MALE,
                new Address("Sonnberg", "89", "Arb", "3253", "Austria"),
                LocalDate.of(1990, 11, 27),
                "+43 680 1397620",
                "ellen.bogen@students.fhv.at",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(ellenBogen);

        Guest klausUhr = Guest.create(guestRepository.nextIdentity(),
                new FullName("Klaus", "Uhr"),
                Gender.MALE,
                new Address("Bayerhamerstrasse", "80", "Gloggnitz", "2640", "Austria"),
                LocalDate.of(1994, 10, 23),
                "+43 680 7653692",
                "klaus.uhr@students.fhv.at",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(klausUhr);

        Guest rosaPanter = Guest.create(guestRepository.nextIdentity(),
                new FullName("Rosa", "Panter"),
                Gender.MALE,
                new Address("Faerberplatz", "42", "Stixneusiedl", "2463", "Austria"),
                LocalDate.of(1978, 3, 30),
                "+43 680 0568920",
                "rosa.panter@students.fhv.at",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(rosaPanter);

        Guest teddyBaer = Guest.create(guestRepository.nextIdentity(),
                new FullName("Teddy", "Baer"),
                Gender.MALE,
                new Address("Gunzing", "85", "Bärnkopf", "3633", "Austria"),
                LocalDate.of(2000, 8, 12),
                "+43 680 1597620",
                "teddy.baer@students.fhv.at",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(teddyBaer);



/*----- Bookings for Stay-----*/
        Booking b1 = Booking.create(
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                bookingRepository.nextIdentity(),
                johannes,
                List.of(swimmingPool,wlan),
                1,
                0,
                "",
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001"
        );
        b1.addRoomCategory(singleRoom, 1);
        this.bookingRepository.add(b1);

        Stay stay1 = Stay.create(stayRepository.nextIdentity(), b1, Map.of(roomS0, false));
        b1.deactivate();
        roomS0.occupy();
        this.stayRepository.add(stay1);

        Booking b2 = Booking.create(
                LocalDate.now().minusDays(7),
                LocalDate.now(),
                bookingRepository.nextIdentity(),
                umut,
                List.of(sauna, fitnessRoom, wlan),
                1,
                0,
                "",
                LocalDate.now().minusDays(7).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001"
        );
        b2.addRoomCategory(singleRoom, 1);
        this.bookingRepository.add(b2);

        Stay stay2 = Stay.create(stayRepository.nextIdentity(), b2, Map.of(roomS1, false));
        b2.deactivate();
        roomS1.occupy();
        this.stayRepository.add(stay2);

        Booking b3 = Booking.create(
                LocalDate.now().minusDays(5),
                LocalDate.now(),
                bookingRepository.nextIdentity(),
                ali,
                List.of(fitnessRoom, wlan),
                2,
                0,
                "",
                LocalDate.now().minusDays(5).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001"
        );
        b3.addRoomCategory(doubleRoom, 1);
        this.bookingRepository.add(b3);

        Stay stay3 = Stay.create(stayRepository.nextIdentity(), b3, Map.of(roomD0, false));
        b3.deactivate();
        roomD0.occupy();
        this.stayRepository.add(stay3);

        Booking b4 = Booking.create(
                LocalDate.now().minusDays(3),
                LocalDate.now().plusDays(1),
                bookingRepository.nextIdentity(),
                dario,
                List.of(sauna, newsPaper),
                2,
                0,
                "",
                LocalDate.now().minusDays(3).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001"
        );
        b4.addRoomCategory(singleRoom, 2);
        this.bookingRepository.add(b4);

        Stay stay4 = Stay.create(stayRepository.nextIdentity(), b4, Map.of(roomS2, false, roomS3, false));
        b4.deactivate();
        roomS2.occupy();
        roomS3.occupy();
        this.stayRepository.add(stay4);

        Booking b5 = Booking.create(
                LocalDate.now().minusDays(4),
                LocalDate.now().plusDays(2),
                bookingRepository.nextIdentity(),
                michael,
                List.of(fitnessRoom, wlan, newsPaper),
                4,
                0,
                "Vegan",
                LocalDate.now().minusDays(4).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001"
        );
        b5.addRoomCategory(doubleRoom, 2);
        this.bookingRepository.add(b5);

        Stay stay5 = Stay.create(stayRepository.nextIdentity(), b5, Map.of(roomD1, false, roomD2, false));
        b5.deactivate();
        roomD1.occupy();
        roomD2.occupy();
        this.stayRepository.add(stay5);

        Booking b6 = Booking.create(
                LocalDate.now().minusDays(5),
                LocalDate.now(),
                bookingRepository.nextIdentity(),
                nickOlaus,
                List.of(wlan),
                10,
                0,
                "",
                LocalDate.now().minusDays(5).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001"
        );
        b6.addRoomCategory(singleRoom, 5);
        b6.addRoomCategory(doubleRoom, 1);
        b6.addRoomCategory(suite, 1);
        this.bookingRepository.add(b6);

        Stay stay6 = Stay.create(stayRepository.nextIdentity(), b6, Map.of(
                roomS4, false,
                roomS5, false,
                roomS6, false,
                roomS7, false,
                roomS8, false,
                roomD3, false,
                roomSU0, false
        ));
        b6.deactivate();
        roomS4.occupy();
        roomS5.occupy();
        roomS6.occupy();
        roomS7.occupy();
        roomS8.occupy();
        roomD3.occupy();
        roomSU0.occupy();
        this.stayRepository.add(stay6);

        Booking b7 = Booking.create(
                LocalDate.now().minusDays(2),
                LocalDate.now().plusDays(1),
                bookingRepository.nextIdentity(),
                rosaPanter,
                List.of(wlan),
                15,
                0,
                "",
                LocalDate.now().minusDays(2).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001"
        );
        b7.addRoomCategory(doubleRoom, 7);
        this.bookingRepository.add(b7);

        Stay stay7 = Stay.create(stayRepository.nextIdentity(), b7, Map.of(
                roomD4, false,
                roomD5, false,
                roomD6, false,
                roomD7, false,
                roomD8, false,
                roomD9, false,
                roomD10, false
        ));
        b7.deactivate();
        roomD4.occupy();
        roomD5.occupy();
        roomD6.occupy();
        roomD7.occupy();
        roomD8.occupy();
        roomD9.occupy();
        roomD10.occupy();
        this.stayRepository.add(stay7);

/*----- Bookings for Checkin-----*/

        Booking b8 = Booking.create(
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                bookingRepository.nextIdentity(),
                rainerZufall,
                List.of(sauna),
                2,
                0,
                "Vegan",
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "002"
        );
        b8.addRoomCategory(singleRoom, 2);
        b8.addRoomCategory(doubleRoom, 2);
        b8.addRoomCategory(juniorSuite, 2);
        this.bookingRepository.add(b8);

        Booking b9 = Booking.create(
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                bookingRepository.nextIdentity(),
                ellenBogen,
                List.of(fitnessRoom, parkingPlace, wlan),
                4,
                0,
                "Vegan",
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "004"
        );
        b9.addRoomCategory(singleRoom, 2);
        b9.addRoomCategory(juniorSuite, 1);
        this.bookingRepository.add(b9);

        Booking b10 = Booking.create(
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                bookingRepository.nextIdentity(),
                frankReich,
                List.of(wlan),
                2,
                0,
                "",
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "005"
        );
        b10.addRoomCategory(doubleRoom, 1);
        this.bookingRepository.add(b10);

        Booking b11 = Booking.create(
                LocalDate.now().plusDays(2),
                LocalDate.now().plusDays(9),
                bookingRepository.nextIdentity(),
                gerdNehr,
                List.of(parkingPlace, newsPaper),
                10,
                0,
                "",
                LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001"
        );
        b11.addRoomCategory(singleRoom, 1);
        b11.addRoomCategory(doubleRoom, 3);
        b11.addRoomCategory(juniorSuite, 1);
        b11.addRoomCategory(suite, 2);
        this.bookingRepository.add(b11);

        Booking b12 = Booking.create(
                LocalDate.now().plusDays(3),
                LocalDate.now().plusDays(6),
                bookingRepository.nextIdentity(),
                hellaWahnsinn,
                List.of(sauna),
                3,
                0,
                "Vegetarian",
                LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001"
        );
        b12.addRoomCategory(singleRoom, 1);
        b12.addRoomCategory(doubleRoom, 1);
        this.bookingRepository.add(b12);

        Booking b13 = Booking.create(
                LocalDate.now().plusDays(5),
                LocalDate.now().plusDays(9),
                bookingRepository.nextIdentity(),
                billYard,
                List.of(sauna, wlan, newsPaper),
                1,
                0,
                "",
                LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001"
        );
        b13.addRoomCategory(suite, 1);
        this.bookingRepository.add(b13);


        Booking b14 = Booking.create(
                LocalDate.now().plusDays(10),
                LocalDate.now().plusDays(12),
                bookingRepository.nextIdentity(),
                kaiSer,
                List.of(sauna),
                1,
                0,
                "Vegan",
                LocalDate.now().plusDays(10).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001"
        );
        b14.addRoomCategory(juniorSuite, 1);
        this.bookingRepository.add(b14);

        Booking b15 = Booking.create(
                LocalDate.now().plusDays(15),
                LocalDate.now().plusDays(20),
                bookingRepository.nextIdentity(),
                klaraFall,
                List.of(parkingPlace),
                2,
                0,
                "Vegan",
                LocalDate.now().plusDays(15).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001"
        );
        b15.addRoomCategory(doubleRoom, 1);
        this.bookingRepository.add(b15);

        Booking b16 = Booking.create(
                LocalDate.now().plusDays(20),
                LocalDate.now().plusDays(24),
                bookingRepository.nextIdentity(),
                klausTrophobie,
                List.of(),
                4,
                0,
                "",
                LocalDate.now().plusDays(20).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001"
        );
        b16.addRoomCategory(doubleRoom, 2);
        this.bookingRepository.add(b16);

        Booking b17 = Booking.create(
                LocalDate.now().plusDays(30),
                LocalDate.now().plusDays(33),
                bookingRepository.nextIdentity(),
                klausUhr,
                List.of(wlan),
                3,
                0,
                "Vegan",
                LocalDate.now().plusDays(30).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001"
        );
        b17.addRoomCategory(singleRoom, 1);
        b17.addRoomCategory(doubleRoom, 2);
        this.bookingRepository.add(b17);
    }
}
