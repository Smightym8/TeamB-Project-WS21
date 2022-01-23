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
        Room roomS110 = Room.create(new RoomName("S100"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS110);

        Room roomS101 = Room.create(new RoomName("S101"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS101);

        Room roomS102 = Room.create(new RoomName("S102"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS102);

        Room roomS103 = Room.create(new RoomName("S103"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS103);

        Room roomS104 = Room.create(new RoomName("S104"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS104);

        Room roomS105 = Room.create(new RoomName("S105"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS105);

        Room roomS201 = Room.create(new RoomName("S201"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS201);

        Room roomS202 = Room.create(new RoomName("S202"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS202);

        Room roomS203 = Room.create(new RoomName("S203"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS203);

        Room roomS204 = Room.create(new RoomName("S204"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS204);

        Room roomS205 = Room.create(new RoomName("S205"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS205);

        Room roomS301 = Room.create(new RoomName("S301"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS301);

        Room roomS302 = Room.create(new RoomName("S302"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS302);

        Room roomS303 = Room.create(new RoomName("S303"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS303);

        Room roomS304 = Room.create(new RoomName("S304"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS304);

        Room roomS305 = Room.create(new RoomName("S305"), RoomStatus.FREE, singleRoom);
        this.roomRepository.add(roomS305);

        Room roomD106 = Room.create(new RoomName("D106"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD106);

        Room roomD107 = Room.create(new RoomName("D107"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD107);

        Room roomD108 = Room.create(new RoomName("D108"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD108);

        Room roomD109 = Room.create(new RoomName("D109"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD109);

        Room roomD110 = Room.create(new RoomName("D110"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD110);

        Room roomD111 = Room.create(new RoomName("D111"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD111);

        Room roomD112 = Room.create(new RoomName("D112"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD112);

        Room roomD113 = Room.create(new RoomName("D113"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD113);

        Room roomD114 = Room.create(new RoomName("D114"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD114);

        Room roomD115 = Room.create(new RoomName("D115"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD115);

        Room roomD116 = Room.create(new RoomName("D116"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD116);

        Room roomD117 = Room.create(new RoomName("D117"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD117);

        Room roomD118 = Room.create(new RoomName("D118"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD118);

        Room roomD119 = Room.create(new RoomName("D119"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD119);

        Room roomD120 = Room.create(new RoomName("D120"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD120);

        Room roomD206 = Room.create(new RoomName("D206"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD206);

        Room roomD207 = Room.create(new RoomName("D207"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD207);

        Room roomD208 = Room.create(new RoomName("D208"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD208);

        Room roomD209 = Room.create(new RoomName("D209"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD209);

        Room roomD210 = Room.create(new RoomName("D210"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD210);

        Room roomD211 = Room.create(new RoomName("D211"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD211);

        Room roomD212 = Room.create(new RoomName("D212"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD212);

        Room roomD213 = Room.create(new RoomName("D213"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD213);

        Room roomD214 = Room.create(new RoomName("D214"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD214);

        Room roomD215 = Room.create(new RoomName("D215"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD215);

        Room roomD216 = Room.create(new RoomName("D216"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD216);

        Room roomD217 = Room.create(new RoomName("D217"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD217);

        Room roomD218 = Room.create(new RoomName("D218"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD218);

        Room roomD219 = Room.create(new RoomName("D219"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD219);

        Room roomD220 = Room.create(new RoomName("D220"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD220);

        Room roomD306 = Room.create(new RoomName("D306"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD306);

        Room roomD307 = Room.create(new RoomName("D307"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD307);

        Room roomD308 = Room.create(new RoomName("D308"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD308);

        Room roomD309 = Room.create(new RoomName("D309"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD309);

        Room roomD310 = Room.create(new RoomName("D310"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD310);

        Room roomD311 = Room.create(new RoomName("D311"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD311);

        Room roomD312 = Room.create(new RoomName("D312"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD312);

        Room roomD313 = Room.create(new RoomName("D313"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD313);

        Room roomD314 = Room.create(new RoomName("D314"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD314);

        Room roomD315 = Room.create(new RoomName("D315"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD315);

        Room roomD316 = Room.create(new RoomName("D316"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD316);

        Room roomD317 = Room.create(new RoomName("D317"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD317);

        Room roomD318 = Room.create(new RoomName("D318"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD318);

        Room roomD319 = Room.create(new RoomName("D319"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD319);

        Room roomD320 = Room.create(new RoomName("D320"), RoomStatus.FREE, doubleRoom);
        this.roomRepository.add(roomD320);

        Room roomJ401 = Room.create(new RoomName("J401"), RoomStatus.FREE, juniorSuite);
        this.roomRepository.add(roomJ401);

        Room roomJ402 = Room.create(new RoomName("J402"), RoomStatus.FREE, juniorSuite);
        this.roomRepository.add(roomJ402);

        Room roomJ403 = Room.create(new RoomName("J403"), RoomStatus.FREE, juniorSuite);
        this.roomRepository.add(roomJ403);

        Room roomJ404 = Room.create(new RoomName("J404"), RoomStatus.FREE, juniorSuite);
        this.roomRepository.add(roomJ404);

        Room roomJ405 = Room.create(new RoomName("J405"), RoomStatus.FREE, juniorSuite);
        this.roomRepository.add(roomJ405);

        Room roomJ406 = Room.create(new RoomName("J406"), RoomStatus.FREE, juniorSuite);
        this.roomRepository.add(roomJ406);

        Room roomJ407 = Room.create(new RoomName("J407"), RoomStatus.FREE, juniorSuite);
        this.roomRepository.add(roomJ407);

        Room roomJ408 = Room.create(new RoomName("J408"), RoomStatus.FREE, juniorSuite);
        this.roomRepository.add(roomJ408);

        Room roomJ409 = Room.create(new RoomName("J409"), RoomStatus.FREE, juniorSuite);
        this.roomRepository.add(roomJ409);

        Room roomJ410 = Room.create(new RoomName("J410"), RoomStatus.FREE, juniorSuite);
        this.roomRepository.add(roomJ410);

        Room roomJ411 = Room.create(new RoomName("J411"), RoomStatus.FREE, juniorSuite);
        this.roomRepository.add(roomJ411);

        Room roomJ412 = Room.create(new RoomName("J412"), RoomStatus.FREE, juniorSuite);
        this.roomRepository.add(roomJ412);

        Room roomJ413 = Room.create(new RoomName("J413"), RoomStatus.FREE, juniorSuite);
        this.roomRepository.add(roomJ413);

        Room roomJ414 = Room.create(new RoomName("J414"), RoomStatus.FREE, juniorSuite);
        this.roomRepository.add(roomJ414);

        Room roomJ415 = Room.create(new RoomName("J415"), RoomStatus.FREE, juniorSuite);
        this.roomRepository.add(roomJ415);

        Room roomSU415 = Room.create(new RoomName("SU415"), RoomStatus.FREE, suite);
        this.roomRepository.add(roomSU415);

        Room roomSU416 = Room.create(new RoomName("SU416"), RoomStatus.FREE, suite);
        this.roomRepository.add(roomSU416);

        Room roomSU417 = Room.create(new RoomName("SU417"), RoomStatus.FREE, suite);
        this.roomRepository.add(roomSU417);

        Room roomSU418 = Room.create(new RoomName("SU418"), RoomStatus.FREE, suite);
        this.roomRepository.add(roomSU418);

        Room roomSU419 = Room.create(new RoomName("SU419"), RoomStatus.FREE, suite);
        this.roomRepository.add(roomSU419);

        Room roomSU420 = Room.create(new RoomName("SU420"), RoomStatus.FREE, suite);
        this.roomRepository.add(roomSU420);


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
                20,
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
                20,
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
                20,
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
                20,
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
                20,
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
                new Address("Bonygasse", "99", "Innerhötzendorf", "4152", "Austria"),
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

        Guest lionelHausberger = Guest.create(guestRepository.nextIdentity(),
                new FullName("Lionel", "Hausberger"),
                Gender.MALE,
                new Address("Hinterbergergasse", "8", "Ebreichsdorf", "6835", "Austria"),
                LocalDate.of(1995, 2, 14),
                "+43 759 5969731",
                "lionel.hausberger@outlook.de",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(lionelHausberger);

        Guest janKoch = Guest.create(guestRepository.nextIdentity(),
                new FullName("Jan", "Koch"),
                Gender.MALE,
                new Address("Dünserstr", "5", "Friedberg", "9921", "Austria"),
                LocalDate.of(1975, 6, 13),
                "+43 634 3305560",
                "jan.koch@gmail.com",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(janKoch);

        Guest ameliaHahn = Guest.create(guestRepository.nextIdentity(),
                new FullName("Amelia", "Hahn"),
                Gender.FEMALE,
                new Address("Schinaglstraße", "6", "Lienz", "3818", "Austria"),
                LocalDate.of(1941, 1, 1),
                "+43 179 4031018",
                "amelia.hahn@gmail.com",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(ameliaHahn);

        Guest annaLenaSchlögl = Guest.create(guestRepository.nextIdentity(),
                new FullName("Anna-Lena", "Schlögl"),
                Gender.FEMALE,
                new Address("Krainerweg", "280", "Wolkersdorf", "2077", "Austria"),
                LocalDate.of(1973, 8, 28),
                "+43 790 7743432",
                "anna-lena.schlögl@yahoo.com",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(annaLenaSchlögl);

        Guest dennisRitter = Guest.create(guestRepository.nextIdentity(),
                new FullName("Dennis", "Ritter"),
                Gender.MALE,
                new Address("Lilly-Hochreiter-Gasse", "84", "Purbach am Neusiedler See", "1620", "Austria"),
                LocalDate.of(1978, 12, 2),
                "+43 845 0558299",
                "dennis.ritter@tdd.at",
                0,
                Collections.emptyList()
        );
        this.guestRepository.add(dennisRitter);

        Guest jonathanThaler = Guest.create(guestRepository.nextIdentity(),
                new FullName("Jonathan", "Thaler"),
                Gender.MALE,
                new Address("Hochschulstraße", "1", "Dornbirn", "6850", "Austria"),
                LocalDate.of(1985, 1, 11),
                "+43 680 1597620",
                "jonathan.thaler@tdd.fhv.at",
                100,
                Collections.emptyList()
        );
        this.guestRepository.add(jonathanThaler);



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

        Stay stay1 = Stay.create(stayRepository.nextIdentity(), b1, Map.of(roomS110, false));
        b1.deactivate();
        roomS110.occupy();
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

        Stay stay2 = Stay.create(stayRepository.nextIdentity(), b2, Map.of(roomS101, false));
        b2.deactivate();
        roomS101.occupy();
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

        Stay stay3 = Stay.create(stayRepository.nextIdentity(), b3, Map.of(roomD106, false));
        b3.deactivate();
        roomD106.occupy();
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

        Stay stay4 = Stay.create(stayRepository.nextIdentity(), b4, Map.of(roomS102, false, roomS103, false));
        b4.deactivate();
        roomS102.occupy();
        roomS103.occupy();
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

        Stay stay5 = Stay.create(stayRepository.nextIdentity(), b5, Map.of(roomD107, false, roomD108, false));
        b5.deactivate();
        roomD107.occupy();
        roomD108.occupy();
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
                roomS104, false,
                roomS105, false,
                roomS201, false,
                roomS202, false,
                roomS203, false,
                roomD109, false,
                roomSU415, false
        ));
        b6.deactivate();
        roomS104.occupy();
        roomS105.occupy();
        roomS201.occupy();
        roomS202.occupy();
        roomS203.occupy();
        roomD109.occupy();
        roomSU415.occupy();
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
                roomD110, false,
                roomD111, false,
                roomD112, false,
                roomD113, false,
                roomD114, false,
                roomD117, false,
                roomD116, false
        ));
        b7.deactivate();
        roomD110.occupy();
        roomD111.occupy();
        roomD112.occupy();
        roomD113.occupy();
        roomD114.occupy();
        roomD117.occupy();
        roomD116.occupy();
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
                LocalDate.now().minusDays(2).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001"
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
                LocalDate.now().minusDays(3).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001"
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
                LocalDate.now().minusDays(5).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001"
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
                LocalDate.now().minusDays(10).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001"
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
                LocalDate.now().minusDays(15).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001"
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
                LocalDate.now().minusDays(20).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001"
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
                LocalDate.now().minusDays(30).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001"
        );
        b17.addRoomCategory(singleRoom, 1);
        b17.addRoomCategory(doubleRoom, 2);
        this.bookingRepository.add(b17);

        Booking b18 = Booking.create(
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                bookingRepository.nextIdentity(),
                jonathanThaler,
                List.of(wlan, fitnessRoom, sauna, swimmingPool, parkingPlace, miniBar),
                2,
                0,
                "",
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "006"
        );
        b18.addRoomCategory(suite, 1);
        this.bookingRepository.add(b18);

        Booking b19 = Booking.create(
                LocalDate.now().plusDays(50),
                LocalDate.now().plusDays(55),
                bookingRepository.nextIdentity(),
                annaLenaSchlögl,
                List.of(wlan),
                3,
                0,
                "Vegan",
                LocalDate.now().minusDays(32).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001"
        );
        b19.addRoomCategory(doubleRoom, 2);
        this.bookingRepository.add(b19);

        Booking b20 = Booking.create(
                LocalDate.now().plusDays(52),
                LocalDate.now().plusDays(57),
                bookingRepository.nextIdentity(),
                dennisRitter,
                List.of(parkingPlace),
                4,
                0,
                "",
                LocalDate.now().minusDays(34).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001"
        );
        b20.addRoomCategory(doubleRoom, 2);
        this.bookingRepository.add(b20);

        Booking b21 = Booking.create(
                LocalDate.now().plusDays(55),
                LocalDate.now().plusDays(60),
                bookingRepository.nextIdentity(),
                ameliaHahn,
                List.of(wlan),
                3,
                0,
                "Vegan",
                LocalDate.now().minusDays(36).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001"
        );
        b21.addRoomCategory(singleRoom, 1);
        this.bookingRepository.add(b21);

        Booking b22 = Booking.create(
                LocalDate.now().plusDays(60),
                LocalDate.now().plusDays(61),
                bookingRepository.nextIdentity(),
                janKoch,
                List.of(wlan, parkingPlace),
                1,
                0,
                "",
                LocalDate.now().minusDays(37).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001"
        );
        b22.addRoomCategory(juniorSuite, 1);
        this.bookingRepository.add(b22);

        Booking b23 = Booking.create(
                LocalDate.now().plusDays(60),
                LocalDate.now().plusDays(66),
                bookingRepository.nextIdentity(),
                lionelHausberger,
                List.of(wlan, fitnessRoom),
                3,
                0,
                "",
                LocalDate.now().minusDays(36).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001"
        );
        b23.addRoomCategory(singleRoom, 1);
        b23.addRoomCategory(doubleRoom, 1);
        this.bookingRepository.add(b23);


        Booking b24 = Booking.create(
                LocalDate.now().plusDays(70),
                LocalDate.now().plusDays(80),
                bookingRepository.nextIdentity(),
                teddyBaer,
                List.of(wlan),
                2,
                0,
                "Vegan",
                LocalDate.now().minusDays(38).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001"
        );
        b24.addRoomCategory(doubleRoom, 1);
        this.bookingRepository.add(b24);


        Booking b25 = Booking.create(
                LocalDate.now().plusDays(90),
                LocalDate.now().plusDays(95),
                bookingRepository.nextIdentity(),
                moniTor,
                List.of(fitnessRoom, sauna),
                2,
                0,
                "",
                LocalDate.now().minusDays(40).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001"
        );
        b25.addRoomCategory(juniorSuite, 1);
        this.bookingRepository.add(b25);
    }
}
