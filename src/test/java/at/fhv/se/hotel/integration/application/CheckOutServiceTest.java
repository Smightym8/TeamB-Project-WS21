package at.fhv.se.hotel.integration.application;

import at.fhv.se.hotel.application.api.CheckOutService;
import at.fhv.se.hotel.application.api.exception.RoomNotFoundException;
import at.fhv.se.hotel.application.api.exception.SeasonNotFoundException;
import at.fhv.se.hotel.application.api.exception.StayNotFoundException;
import at.fhv.se.hotel.application.dto.InvoiceDTO;
import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;
import at.fhv.se.hotel.domain.model.guest.*;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomName;
import at.fhv.se.hotel.domain.model.room.RoomStatus;
import at.fhv.se.hotel.domain.model.roomcategory.*;
import at.fhv.se.hotel.domain.model.season.Season;
import at.fhv.se.hotel.domain.model.season.SeasonId;
import at.fhv.se.hotel.domain.model.season.SeasonName;
import at.fhv.se.hotel.domain.model.service.Price;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.service.ServiceId;
import at.fhv.se.hotel.domain.model.service.ServiceName;
import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.model.stay.StayId;
import at.fhv.se.hotel.domain.repository.InvoiceRepository;
import at.fhv.se.hotel.domain.repository.RoomRepository;
import at.fhv.se.hotel.domain.repository.SeasonRepository;
import at.fhv.se.hotel.domain.repository.StayRepository;
import at.fhv.se.hotel.domain.services.api.RoomCategoryPriceService;
import org.apache.xpath.operations.Bool;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CheckOutServiceTest {

    @Autowired
    CheckOutService checkOutService;

    @MockBean
    StayRepository stayRepository;

    @MockBean
    SeasonRepository seasonRepository;

    @MockBean
    RoomCategoryPriceService roomCategoryPriceRepository;

    @MockBean
    InvoiceRepository invoiceRepository;

    @MockBean
    RoomRepository roomRepository;

    @Test
    void given_stay_and_guest_without_discount_when_createinvoice_then_returnexpectedinvoice() throws StayNotFoundException, RoomNotFoundException, SeasonNotFoundException {
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
                0,
                Collections.emptyList()
        );

        List<RoomCategory> categoriesExpected = List.of(
                RoomCategory.create(new RoomCategoryId("1"),
                        new RoomCategoryName("Single Room"),
                        new Description("This is a single room"))
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
                LocalDate.of(2021, 8, 1),
                LocalDate.of(2021, 8, 10),
                new BookingId("1"),
                guestExpected,
                servicesExpected,
                2,
                1,
                "Nothing",
                "20210801001"
        );

        categoriesExpected.forEach(roomCategory -> bookingExpected.addRoomCategory(roomCategory, 1));

        Season summerSeason = Season.create(
                new SeasonId("1"),
                new SeasonName("Winter "),
                LocalDate.of(2021, 6, 1),
                LocalDate.of(2021, 11, 30)
        );

        List<RoomCategoryPrice> roomCategoryPricesExpected = List.of(
                RoomCategoryPrice.create(
                        new RoomCategoryPriceId("1"),
                        summerSeason,
                        categoriesExpected.get(0),
                        new BigDecimal("300").setScale(2, RoundingMode.CEILING)
                )
        );

        String roomNameExpected = "Room 1";
        RoomStatus roomStatusExpected = RoomStatus.FREE;

        Map<Room, Boolean> roomsExpected = Map.of(
                Room.create(
                        new RoomName(roomNameExpected),
                        roomStatusExpected,
                        categoriesExpected.get(0)
                ), false
        );

        List<Room> roomsExpectedList = new ArrayList<>(roomsExpected.keySet());

        List<String> roomNamesExpected = Arrays.asList(roomNameExpected);

        StayId stayIdExpected = new StayId("1");
        Stay stayExpected = Stay.create(stayIdExpected, bookingExpected, roomsExpected);

        int amountOfNightsExpected = 9;
        BigDecimal localTaxPerPersonExpected = new BigDecimal("0.76").setScale(2, RoundingMode.CEILING);
        BigDecimal localTaxTotalExpected = new BigDecimal("1.52").setScale(2, RoundingMode.CEILING);
        BigDecimal valueAddedTaxInPercentExpected = new BigDecimal("0.1");
        BigDecimal totalNetAmountBeforeDiscountExpected = new BigDecimal("4500").setScale(2, RoundingMode.CEILING);
        BigDecimal totalNetAmountAfterDiscountExpected = new BigDecimal("4500").setScale(2, RoundingMode.CEILING);
        BigDecimal totalNetAmountAfterLocalTaxExpected = new BigDecimal("4501.52").setScale(2, RoundingMode.CEILING);
        BigDecimal valueAddedTaxInEuroExpected = new BigDecimal("450.00").setScale(2, RoundingMode.CEILING);
        BigDecimal totalGrossAmountExpected = new BigDecimal("4951.52").setScale(2, RoundingMode.CEILING);
        String action = "checkOut";

        Mockito.when(invoiceRepository.invoicesByDate(LocalDate.now())).thenReturn(Collections.emptyList());
        Mockito.when(stayRepository.stayById(stayIdExpected)).thenReturn(Optional.of(stayExpected));
        Mockito.when(roomRepository.roomByName(new RoomName(roomNameExpected))).thenReturn(Optional.of(roomsExpectedList.get(0)));

        // Mock each date which occurs in the loop of the calculation to return the proper season
        for (int i = 1; i <= 10; i++) {
            Mockito.when(seasonRepository.seasonByDate(LocalDate.of(2021, 8, i)))
                    .thenReturn(Optional.of(summerSeason));
        }

        Mockito.when(roomCategoryPriceRepository.by(categoriesExpected.get(0), summerSeason.getSeasonId()))
                .thenReturn(roomCategoryPricesExpected.get(0));

        // when
        InvoiceDTO invoiceActual = checkOutService.createInvoice(stayExpected.getStayId().id(), roomNamesExpected, action);

        // then
        assertEquals(stayExpected.getGuest().getName().firstName(), invoiceActual.guestFirstName());
        assertEquals(stayExpected.getGuest().getName().lastName(), invoiceActual.guestLastName());
        assertEquals(stayExpected.getGuest().getAddress().streetName(), invoiceActual.streetName());
        assertEquals(stayExpected.getGuest().getAddress().streetNumber(), invoiceActual.streetNumber());
        assertEquals(stayExpected.getGuest().getAddress().zipCode(), invoiceActual.zipCode());
        assertEquals(stayExpected.getGuest().getAddress().city(), invoiceActual.city());
        assertEquals(stayExpected.getCheckInDate(), invoiceActual.checkInDate());
        assertEquals(stayExpected.getCheckOutDate(), invoiceActual.checkOutDate());
        assertEquals(amountOfNightsExpected, invoiceActual.amountOfNights());
        assertEquals(localTaxPerPersonExpected, invoiceActual.localTaxPerPerson());
        assertEquals(localTaxTotalExpected, invoiceActual.localTaxTotal());
        assertEquals(valueAddedTaxInPercentExpected, invoiceActual.valueAddedTaxInPercent());
        assertEquals(totalNetAmountBeforeDiscountExpected, invoiceActual.totalNetAmountBeforeDiscount());
        assertEquals(totalNetAmountAfterDiscountExpected, invoiceActual.totalNetAmountAfterDiscount());
        assertEquals(totalNetAmountAfterLocalTaxExpected, invoiceActual.totalNetAmountAfterLocalTax());
        assertEquals(valueAddedTaxInEuroExpected, invoiceActual.valueAddedTaxInEuro());
        assertEquals(totalGrossAmountExpected, invoiceActual.totalGrossAmount());
    }

    @Test
    void given_stay_and_guest_with_discount_when_createinvoice_then_returnexpectedinvoice() throws StayNotFoundException, RoomNotFoundException, SeasonNotFoundException {
        // given
        double discountInPercentExpected = 10.0;
        Guest guestExpected = Guest.create(new GuestId("1"),
                new FullName("Michael", "Spiegel"),
                Gender.MALE,
                new Address("Hochschulstraße",
                        "1", "Dornbirn",
                        "6850", "Austria"),
                LocalDate.of(1999, 3, 20),
                "+43 660 123 456 789",
                "michael.spiegel@students.fhv.at",
                discountInPercentExpected,
                Collections.emptyList()
        );

        List<RoomCategory> categoriesExpected = List.of(
                RoomCategory.create(new RoomCategoryId("1"),
                        new RoomCategoryName("Single Room"),
                        new Description("This is a single room"))
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
                LocalDate.of(2021, 8, 1),
                LocalDate.of(2021, 8, 10),
                new BookingId("1"),
                guestExpected,
                servicesExpected,
                2,
                1,
                "Nothing",
                "20210801001"
        );

        categoriesExpected.forEach(roomCategory -> bookingExpected.addRoomCategory(roomCategory, 1));

        Season summerSeason = Season.create(
                new SeasonId("1"),
                new SeasonName("Winter "),
                LocalDate.of(2021, 6, 1),
                LocalDate.of(2021, 11, 30)
        );

        List<RoomCategoryPrice> roomCategoryPricesExpected = List.of(
                RoomCategoryPrice.create(
                        new RoomCategoryPriceId("1"),
                        summerSeason,
                        categoriesExpected.get(0),
                        new BigDecimal("300").setScale(2, RoundingMode.CEILING)
                )
        );

        String roomNameExpected = "Room 1";
        RoomStatus roomStatusExpected = RoomStatus.FREE;

        Map<Room, Boolean> roomsExpected = Map.of(
                Room.create(
                        new RoomName(roomNameExpected),
                        roomStatusExpected,
                        categoriesExpected.get(0)
                ), false
        );

        List<Room> roomsExpectedList = new ArrayList<>(roomsExpected.keySet());

        List<String> roomNamesExpected = Arrays.asList(roomNameExpected);

        StayId stayIdExpected = new StayId("1");
        Stay stayExpected = Stay.create(stayIdExpected, bookingExpected, roomsExpected);

        int amountOfNightsExpected = 9;
        BigDecimal localTaxPerPersonExpected = new BigDecimal("0.76").setScale(2, RoundingMode.CEILING);
        BigDecimal localTaxTotalExpected = new BigDecimal("1.52").setScale(2, RoundingMode.CEILING);
        BigDecimal valueAddedTaxInPercentExpected = new BigDecimal("0.1");
        BigDecimal totalNetAmountBeforeDiscountExpected = new BigDecimal("4500").setScale(2, RoundingMode.CEILING);
        BigDecimal discountInEuroExpected = new BigDecimal("450").setScale(2, RoundingMode.CEILING);
        BigDecimal totalNetAmountAfterDiscountExpected = new BigDecimal("4050").setScale(2, RoundingMode.CEILING);
        BigDecimal totalNetAmountAfterLocalTaxExpected = new BigDecimal("4051.52").setScale(2, RoundingMode.CEILING);
        BigDecimal valueAddedTaxInEuroExpected = new BigDecimal("405.00").setScale(2, RoundingMode.CEILING);
        BigDecimal totalGrossAmountExpected = new BigDecimal("4456.52").setScale(2, RoundingMode.CEILING);
        String action = "checkOut";

        Mockito.when(invoiceRepository.invoicesByDate(LocalDate.now())).thenReturn(Collections.emptyList());
        Mockito.when(stayRepository.stayById(stayIdExpected)).thenReturn(Optional.of(stayExpected));
        Mockito.when(roomRepository.roomByName(new RoomName(roomNameExpected))).thenReturn(Optional.of(roomsExpectedList.get(0)));

        // Mock each date which occurs in the loop of the calculation to return the proper season
        for (int i = 1; i <= 10; i++) {
            Mockito.when(seasonRepository.seasonByDate(LocalDate.of(2021, 8, i)))
                    .thenReturn(Optional.of(summerSeason));
        }

        Mockito.when(roomCategoryPriceRepository.by(categoriesExpected.get(0), summerSeason.getSeasonId()))
                .thenReturn(roomCategoryPricesExpected.get(0));

        // when
        InvoiceDTO invoiceActual = checkOutService.createInvoice(stayExpected.getStayId().id(), roomNamesExpected, action);

        // then
        assertEquals(stayExpected.getGuest().getName().firstName(), invoiceActual.guestFirstName());
        assertEquals(stayExpected.getGuest().getName().lastName(), invoiceActual.guestLastName());
        assertEquals(stayExpected.getGuest().getAddress().streetName(), invoiceActual.streetName());
        assertEquals(stayExpected.getGuest().getAddress().streetNumber(), invoiceActual.streetNumber());
        assertEquals(stayExpected.getGuest().getAddress().zipCode(), invoiceActual.zipCode());
        assertEquals(stayExpected.getGuest().getAddress().city(), invoiceActual.city());
        assertEquals(stayExpected.getCheckInDate(), invoiceActual.checkInDate());
        assertEquals(stayExpected.getCheckOutDate(), invoiceActual.checkOutDate());
        assertEquals(amountOfNightsExpected, invoiceActual.amountOfNights());
        assertEquals(localTaxPerPersonExpected, invoiceActual.localTaxPerPerson());
        assertEquals(localTaxTotalExpected, invoiceActual.localTaxTotal());
        assertEquals(valueAddedTaxInPercentExpected, invoiceActual.valueAddedTaxInPercent());
        assertEquals(totalNetAmountBeforeDiscountExpected, invoiceActual.totalNetAmountBeforeDiscount());
        assertEquals(discountInPercentExpected, invoiceActual.discountInPercent());
        assertEquals(discountInEuroExpected, invoiceActual.discountInEuro());
        assertEquals(totalNetAmountAfterDiscountExpected, invoiceActual.totalNetAmountAfterDiscount());
        assertEquals(totalNetAmountAfterLocalTaxExpected, invoiceActual.totalNetAmountAfterLocalTax());
        assertEquals(valueAddedTaxInEuroExpected, invoiceActual.valueAddedTaxInEuro());
        assertEquals(totalGrossAmountExpected, invoiceActual.totalGrossAmount());
    }

    @Test
    void given_existingstay_whencheckout_then_stayInactive() throws StayNotFoundException, RoomNotFoundException, SeasonNotFoundException {
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
                0,
                Collections.emptyList()
        );

        List<RoomCategory> categoriesExpected = List.of(
                RoomCategory.create(new RoomCategoryId("1"),
                        new RoomCategoryName("Single Room"),
                        new Description("This is a single room"))
        );

        List<Service> servicesExpected = Arrays.asList(
                Service.create(new ServiceId("1"),
                        new ServiceName("TV"),
                        new Price(new BigDecimal("100").setScale(2, RoundingMode.CEILING))),
                Service.create(new ServiceId("2"),
                        new ServiceName("Breakfast"),
                        new Price(new BigDecimal("100").setScale(2, RoundingMode.CEILING)))
        );

        Booking bookingExpected = Booking.create(
                LocalDate.of(2021, 8, 1),
                LocalDate.of(2021, 8, 10),
                new BookingId("1"),
                guestExpected,
                servicesExpected,
                2,
                1,
                "Nothing",
                "20210801001"
        );

        categoriesExpected.forEach(roomCategory -> bookingExpected.addRoomCategory(roomCategory, 1));

        Season summerSeason = Season.create(
                new SeasonId("1"),
                new SeasonName("Winter "),
                LocalDate.of(2021, 6, 1),
                LocalDate.of(2021, 11, 30)
        );

        List<RoomCategoryPrice> roomCategoryPricesExpected = List.of(
                RoomCategoryPrice.create(
                        new RoomCategoryPriceId("1"),
                        summerSeason,
                        categoriesExpected.get(0),
                        new BigDecimal("300").setScale(2, RoundingMode.CEILING)
                )
        );

        List<String> roomNamesExpected = List.of("Room 1");
        RoomStatus roomStatusExpected = RoomStatus.FREE;

        Map<Room, Boolean> roomsExpected = Map.of(
                Room.create(
                        new RoomName(roomNamesExpected.get(0)),
                        roomStatusExpected,
                        categoriesExpected.get(0)
                ), false
        );

        List<Room> roomsExpectedList = new ArrayList<>(roomsExpected.keySet());

        StayId stayIdExpected = new StayId("1");
        Stay stayExpected = Stay.create(stayIdExpected, bookingExpected, roomsExpected);
        String action = "checkOut";

        Mockito.when(invoiceRepository.invoicesByDate(LocalDate.now())).thenReturn(Collections.emptyList());
        Mockito.when(stayRepository.stayById(stayIdExpected)).thenReturn(Optional.of(stayExpected));
        Mockito.when(roomRepository.roomByName(new RoomName(roomNamesExpected.get(0)))).thenReturn(Optional.of(roomsExpectedList.get(0)));

        for(int i = 1; i <= 10 ; i++) {
            Mockito.when(seasonRepository.seasonByDate(LocalDate.of(2021, 8, i)))
                    .thenReturn(Optional.of(summerSeason));
        }

        Mockito.when(roomCategoryPriceRepository.by(categoriesExpected.get(0), summerSeason.getSeasonId()))
                .thenReturn(roomCategoryPricesExpected.get(0));

        // when
        checkOutService.checkOut(stayExpected.getStayId().id(), roomNamesExpected, action);

        // then
        assertFalse(stayExpected.isActive());
    }

    @Test
    public void given_stayandselectedrooms_when_saveinvoice_then_setroomispaid() throws StayNotFoundException, RoomNotFoundException, SeasonNotFoundException {
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
                0,
                Collections.emptyList()
        );

        List<RoomCategory> categoriesExpected = List.of(
                RoomCategory.create(new RoomCategoryId("1"),
                        new RoomCategoryName("Single Room"),
                        new Description("This is a single room"))
        );

        List<Service> servicesExpected = Arrays.asList(
                Service.create(new ServiceId("1"),
                        new ServiceName("TV"),
                        new Price(new BigDecimal("100"))),
                Service.create(new ServiceId("2"),
                        new ServiceName("Breakfast"),
                        new Price(new BigDecimal("100")))
        );

        Booking bookingExpectedbooking = Booking.create(
                LocalDate.of(2021, 8, 1),
                LocalDate.of(2021, 8, 10),
                new BookingId("1"),
                guestExpected,
                servicesExpected,
                2,
                1,
                "Nothing",
                "20210801001"
        );

        categoriesExpected.forEach(roomCategory -> bookingExpectedbooking.addRoomCategory(roomCategory, 1));

        Season summerSeason = Season.create(
                new SeasonId("1"),
                new SeasonName("Winter "),
                LocalDate.of(2021, 6, 1),
                LocalDate.of(2021, 11, 30)
        );

        List<RoomCategoryPrice> roomCategoryPricesExpected = List.of(
                RoomCategoryPrice.create(
                        new RoomCategoryPriceId("1"),
                        summerSeason,
                        categoriesExpected.get(0),
                        new BigDecimal("300").setScale(2, RoundingMode.CEILING)
                )
        );

        String roomNameExpected = "Room 1";
        RoomStatus roomStatusExpected = RoomStatus.FREE;

        Map<Room, Boolean> roomsExpected = new HashMap<>(Map.of(
                Room.create(
                        new RoomName(roomNameExpected),
                        roomStatusExpected,
                        categoriesExpected.get(0)
                ), false
        ));

        List<Room> roomsExpectedList = new ArrayList<>(roomsExpected.keySet());

        List<String> roomNamesExpected = Arrays.asList(roomNameExpected);

        StayId stayIdExpected = new StayId("1");
        Stay stayExpected = Stay.create(stayIdExpected, bookingExpectedbooking, roomsExpected);
        String action = "createInvoice";

        Mockito.when(invoiceRepository.invoicesByDate(LocalDate.now())).thenReturn(Collections.emptyList());
        Mockito.when(stayRepository.stayById(stayIdExpected)).thenReturn(Optional.of(stayExpected));
        Mockito.when(roomRepository.roomByName(new RoomName(roomNameExpected))).thenReturn(Optional.of(roomsExpectedList.get(0)));

        for(int i = 1; i <= 10 ; i++) {
            Mockito.when(seasonRepository.seasonByDate(LocalDate.of(2021, 8, i)))
                    .thenReturn(Optional.of(summerSeason));
        }

        Mockito.when(roomCategoryPriceRepository.by(categoriesExpected.get(0), summerSeason.getSeasonId()))
                .thenReturn(roomCategoryPricesExpected.get(0));

        //when
        checkOutService.saveInvoice(stayIdExpected.id(), roomNamesExpected, action);

        //then
        for (Map.Entry<Room, Boolean> entry : roomsExpected.entrySet()) {
            assertTrue(entry.getValue());
        }
    }

    @Test
    public void given_missingStay_when_checkOut_then_StayNotFoundExceptionIsThrown() {
        // given
        StayId stayIdExpected = new StayId("1");

        List<String> roomNames = List.of("101");
        String action = "checkOut";

        Mockito.when(stayRepository.stayById(stayIdExpected)).thenReturn(Optional.empty());

        // when ... then
        Exception exception = assertThrows(StayNotFoundException.class, () -> {
            checkOutService.checkOut(stayIdExpected.id(), roomNames, action);
        });

        String expectedMessage = "Check out failed! Stay with id " + stayIdExpected.id() + " doesn't exist.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void given_missingStay_when_createInvoice_then_StayNotFoundExceptionIsThrown() {
        // given
        StayId stayIdExpected = new StayId("1");

        List<String> roomNames = List.of("101");
        String action = "checkOut";

        Mockito.when(stayRepository.stayById(stayIdExpected)).thenReturn(Optional.empty());

        // when ... then
        Exception exception = assertThrows(StayNotFoundException.class,
                () -> checkOutService.createInvoice(stayIdExpected.id(), roomNames, action));

        String expectedMessage = "Creating invoice failed! Stay with id " + stayIdExpected.id() + " not found";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void given_missingStay_when_saveInvoice_then_StayNotFoundExceptionIsThrown() {
        // given
        StayId stayIdExpected = new StayId("1");

        List<String> roomNames = List.of("101");
        String action = "checkOut";

        Mockito.when(stayRepository.stayById(stayIdExpected)).thenReturn(Optional.empty());

        // when ... then
        Exception exception = assertThrows(StayNotFoundException.class,
                () -> checkOutService.saveInvoice(stayIdExpected.id(), roomNames, action)
        );

        String expectedMessage = "Saving invoice failed! Stay with id " + stayIdExpected.id() + " not found";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}