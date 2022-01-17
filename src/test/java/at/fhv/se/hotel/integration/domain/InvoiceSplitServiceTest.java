package at.fhv.se.hotel.integration.domain;

import at.fhv.se.hotel.application.api.exception.GuestNotFoundException;
import at.fhv.se.hotel.application.api.exception.RoomNotFoundException;
import at.fhv.se.hotel.application.api.exception.SeasonNotFoundException;
import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;
import at.fhv.se.hotel.domain.model.guest.*;
import at.fhv.se.hotel.domain.model.invoice.Invoice;
import at.fhv.se.hotel.domain.model.invoice.InvoiceId;
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
import at.fhv.se.hotel.domain.repository.RoomCategoryPriceRepository;
import at.fhv.se.hotel.domain.repository.RoomRepository;
import at.fhv.se.hotel.domain.repository.SeasonRepository;
import at.fhv.se.hotel.domain.services.api.InvoiceSplitService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class InvoiceSplitServiceTest {
    @Autowired
    InvoiceSplitService invoiceSplitService;

    @MockBean
    RoomCategoryPriceRepository roomCategoryPriceRepository;

    @MockBean
    RoomRepository roomRepository;

    @MockBean
    InvoiceRepository invoiceRepository;

    @MockBean
    SeasonRepository seasonRepository;

    @Test
    void given_invoicedetails_when_splitInvoice_then_returnexpectedamount() throws RoomNotFoundException, SeasonNotFoundException {
        // given
        Guest guest = Guest.create(
                new GuestId("1"),
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

        RoomCategory category = RoomCategory.create(
                new RoomCategoryId("1"),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        List<Service> services = Arrays.asList(
                Service.create(new ServiceId("1"),
                        new ServiceName("TV"),
                        new Price(new BigDecimal("100").setScale(2, RoundingMode.CEILING))),
                Service.create(new ServiceId("2"),
                        new ServiceName("Breakfast"),
                        new Price(new BigDecimal("100").setScale(2, RoundingMode.CEILING)))
        );

        Booking booking = Booking.create(
                LocalDate.of(2021, 12, 26),
                LocalDate.of(2021,12,29),
                new BookingId("1"),
                guest,
                services,
                2,
                1,
                "",
                "20211226001"
        );
        booking.addRoomCategory(category, 1);

        Map<Room, Boolean> rooms = Map.of(
                Room.create(new RoomName("101"), RoomStatus.FREE, category), false,
                Room.create(new RoomName("102"), RoomStatus.FREE, category), false,
                Room.create(new RoomName("103"), RoomStatus.FREE, category), false
        );

        Season winterSeason = Season.create(
                new SeasonId("1"),
                new SeasonName("Winter "),
                LocalDate.of(2021, 12, 1),
                LocalDate.of(2022, 1, 31)
        );

        RoomCategoryPrice price = RoomCategoryPrice.create(
                new RoomCategoryPriceId("1"),
                winterSeason,
                category,
                new BigDecimal("600").setScale(2, RoundingMode.CEILING)
        );

        List<Room> roomsExpected = new ArrayList<>(rooms.keySet());

        List<String> selectedRooms = List.of("101");

        StayId stayIdExpected = new StayId("1");
        Stay stayExpected = Stay.create(stayIdExpected, booking, rooms);

        String invoiceNumberExpected = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001";
        int amountOfNightsExpected = 3;
        BigDecimal localTaxPerPersonExpected = new BigDecimal("0.76").setScale(2, RoundingMode.CEILING);
        BigDecimal localTaxTotalExpected = new BigDecimal("0").setScale(2, RoundingMode.CEILING);
        BigDecimal valueAddedTaxInPercentExpected = new BigDecimal("0.10").setScale(2, RoundingMode.CEILING);
        BigDecimal totalNetAmountBeforeDiscountExpected = new BigDecimal("2400").setScale(2, RoundingMode.CEILING);
        BigDecimal totalNetAmountAfterDiscountExpected = new BigDecimal("2400").setScale(2, RoundingMode.CEILING);
        BigDecimal totalNetAmountAfterLocalTaxExpected = new BigDecimal("2400").setScale(2, RoundingMode.CEILING);
        BigDecimal valueAddedTaxInEuroExpected = new BigDecimal("240.00").setScale(2, RoundingMode.CEILING);
        BigDecimal totalGrossAmountExpected = new BigDecimal("2640").setScale(2, RoundingMode.CEILING);

        InvoiceId invoiceId = new InvoiceId("1");

        String action = "createInvoice";

        // when
        Mockito.when(invoiceRepository.amountOfInvoicesByDate(LocalDate.now())).thenReturn(0);

        Mockito.when(roomRepository.roomByName(new RoomName("101"))).thenReturn(java.util.Optional.of(roomsExpected.get(0)));

        Mockito.when(seasonRepository.seasonByDate(LocalDate.of(2021, 12, 26)))
                .thenReturn(Optional.of(winterSeason));
        Mockito.when(seasonRepository.seasonByDate(LocalDate.of(2021, 12, 27)))
                .thenReturn(Optional.of(winterSeason));
        Mockito.when(seasonRepository.seasonByDate(LocalDate.of(2021, 12, 28)))
                .thenReturn(Optional.of(winterSeason));
        Mockito.when(seasonRepository.seasonByDate(LocalDate.of(2021, 12, 29)))
                .thenReturn(Optional.of(winterSeason));

        Mockito.when(roomCategoryPriceRepository.priceBySeasonAndCategory(winterSeason.getSeasonId(), roomsExpected.get(0).getRoomCategory().getRoomCategoryId()))
                .thenReturn(java.util.Optional.of(price));

        Mockito.when(invoiceRepository.nextIdentity()).thenReturn(invoiceId);

        Invoice invoice = invoiceSplitService.splitInvoice(stayExpected, selectedRooms, action);

        // then
        assertEquals(invoiceNumberExpected, invoice.getInvoiceNumber());
        assertEquals(amountOfNightsExpected, invoice.getAmountOfNights());
        assertEquals(localTaxPerPersonExpected, invoice.getLocalTaxPerPerson());
        assertEquals(localTaxTotalExpected, invoice.getLocalTaxTotal());
        assertEquals(valueAddedTaxInPercentExpected, invoice.getValueAddedTaxInPercent());
        assertEquals(totalNetAmountBeforeDiscountExpected, invoice.getTotalNetAmountBeforeDiscount());
        assertEquals(totalNetAmountAfterDiscountExpected, invoice.getTotalNetAmountAfterDiscount());
        assertEquals(totalNetAmountAfterLocalTaxExpected, invoice.getTotalNetAmountAfterLocalTax());
        assertEquals(valueAddedTaxInEuroExpected, invoice.getValueAddedTaxInEuro());
        assertEquals(totalGrossAmountExpected, invoice.getTotalGrossAmount());
    }

    @Test
    void given_2rooms_when_splitInvoice_then_returnexpectedamount() throws RoomNotFoundException, SeasonNotFoundException {
        // given
        Guest guest = Guest.create(
                new GuestId("1"),
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

        RoomCategory category = RoomCategory.create(
                new RoomCategoryId("1"),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        List<Service> services = Arrays.asList(
                Service.create(new ServiceId("1"),
                        new ServiceName("TV"),
                        new Price(new BigDecimal("100").setScale(2, RoundingMode.CEILING))),
                Service.create(new ServiceId("2"),
                        new ServiceName("Breakfast"),
                        new Price(new BigDecimal("100").setScale(2, RoundingMode.CEILING)))
        );

        Booking booking = Booking.create(
                LocalDate.of(2021, 12, 26),
                LocalDate.of(2021,12,29),
                new BookingId("1"),
                guest,
                services,
                2,
                1,
                "",
                "20211226001"
        );
        booking.addRoomCategory(category, 1);

        Map<Room, Boolean> rooms = Map.of(
                Room.create(new RoomName("101"), RoomStatus.FREE, category), false,
                Room.create(new RoomName("102"), RoomStatus.FREE, category), false,
                Room.create(new RoomName("103"), RoomStatus.FREE, category), false
        );

        Season winterSeason = Season.create(
                new SeasonId("1"),
                new SeasonName("Winter "),
                LocalDate.of(2021, 12, 1),
                LocalDate.of(2022, 1, 31)
        );

        RoomCategoryPrice price = RoomCategoryPrice.create(
                new RoomCategoryPriceId("1"),
                winterSeason,
                category,
                new BigDecimal("600").setScale(2, RoundingMode.CEILING)
        );

        List<Room> roomsExpected = new ArrayList<>(rooms.keySet());

        List<String> selectedRooms = List.of("101", "102");

        StayId stayIdExpected = new StayId("1");
        Stay stayExpected = Stay.create(stayIdExpected, booking, rooms);

        String invoiceNumberExpected = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001";
        int amountOfNightsExpected = 3;
        BigDecimal localTaxPerPersonExpected = new BigDecimal("0.76").setScale(2, RoundingMode.CEILING);
        BigDecimal localTaxTotalExpected = new BigDecimal("0").setScale(2, RoundingMode.CEILING);
        BigDecimal valueAddedTaxInPercentExpected = new BigDecimal("0.10").setScale(2, RoundingMode.CEILING);
        BigDecimal totalNetAmountBeforeDiscountExpected = new BigDecimal("4800").setScale(2, RoundingMode.CEILING);
        BigDecimal totalNetAmountAfterDiscountExpected = new BigDecimal("4800").setScale(2, RoundingMode.CEILING);
        BigDecimal totalNetAmountAfterLocalTaxExpected = new BigDecimal("4800").setScale(2, RoundingMode.CEILING);
        BigDecimal valueAddedTaxInEuroExpected = new BigDecimal("480.00").setScale(2, RoundingMode.CEILING);
        BigDecimal totalGrossAmountExpected = new BigDecimal("5280").setScale(2, RoundingMode.CEILING);

        String action = "createInvoice";

        InvoiceId invoiceId = new InvoiceId("1");

        // when
        Mockito.when(invoiceRepository.amountOfInvoicesByDate(LocalDate.now())).thenReturn(0);

        Mockito.when(roomRepository.roomByName(new RoomName("101"))).thenReturn(java.util.Optional.of(roomsExpected.get(0)));
        Mockito.when(roomRepository.roomByName(new RoomName("102"))).thenReturn(java.util.Optional.of(roomsExpected.get(1)));

        for (int i = 26; i <= 29; i++) {
            Mockito.when(seasonRepository.seasonByDate(LocalDate.of(2021, 12, i)))
                    .thenReturn(Optional.of(winterSeason));
        }

        Mockito.when(roomCategoryPriceRepository.priceBySeasonAndCategory(winterSeason.getSeasonId(), roomsExpected.get(0).getRoomCategory().getRoomCategoryId()))
                .thenReturn(java.util.Optional.of(price));

        Mockito.when(invoiceRepository.nextIdentity()).thenReturn(invoiceId);

        Invoice invoice = invoiceSplitService.splitInvoice(stayExpected, selectedRooms, action);

        // then
        assertEquals(invoiceNumberExpected, invoice.getInvoiceNumber());
        assertEquals(amountOfNightsExpected, invoice.getAmountOfNights());
        assertEquals(localTaxPerPersonExpected, invoice.getLocalTaxPerPerson());
        assertEquals(localTaxTotalExpected, invoice.getLocalTaxTotal());
        assertEquals(valueAddedTaxInPercentExpected, invoice.getValueAddedTaxInPercent());
        assertEquals(totalNetAmountBeforeDiscountExpected, invoice.getTotalNetAmountBeforeDiscount());
        assertEquals(totalNetAmountAfterDiscountExpected, invoice.getTotalNetAmountAfterDiscount());
        assertEquals(totalNetAmountAfterLocalTaxExpected, invoice.getTotalNetAmountAfterLocalTax());
        assertEquals(valueAddedTaxInEuroExpected, invoice.getValueAddedTaxInEuro());
        assertEquals(totalGrossAmountExpected, invoice.getTotalGrossAmount());
    }

    @Test
    public void given_missing_season_when_splitInvoice_then_seasonNotFoundExceptionIsThrown() {
        // given
        RoomCategory category = RoomCategory.create(
                new RoomCategoryId("1"),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        List<Service> services = Arrays.asList(
                Service.create(new ServiceId("1"),
                        new ServiceName("TV"),
                        new Price(new BigDecimal("100").setScale(2, RoundingMode.CEILING))),
                Service.create(new ServiceId("2"),
                        new ServiceName("Breakfast"),
                        new Price(new BigDecimal("100").setScale(2, RoundingMode.CEILING)))
        );

        Guest guest = Guest.create(
                new GuestId("1"),
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

        Booking booking = Booking.create(
                LocalDate.of(2021, 12, 26),
                LocalDate.of(2021,12,29),
                new BookingId("1"),
                guest,
                services,
                2,
                1,
                "",
                "20211226001"
        );
        booking.addRoomCategory(category, 1);

        Map<Room, Boolean> rooms = Map.of(
                Room.create(new RoomName("101"), RoomStatus.FREE, category), false,
                Room.create(new RoomName("102"), RoomStatus.FREE, category), false,
                Room.create(new RoomName("103"), RoomStatus.FREE, category), false
        );

        List<String> roomNames = List.of(
                "101",
                "102",
                "103"
        );

        StayId stayIdExpected = new StayId("1");
        Stay stayExpected = Stay.create(stayIdExpected, booking, rooms);

        String actionExpected = "checkOut";

        Mockito.when(invoiceRepository.amountOfInvoicesByDate(LocalDate.now())).thenReturn(0);
        Mockito.when(seasonRepository.seasonByDate(stayExpected.getCheckInDate())).thenReturn(Optional.empty());

        // when ... then
        Exception exception = assertThrows(SeasonNotFoundException.class, () -> {
            invoiceSplitService.splitInvoice(stayExpected, roomNames, actionExpected);
        });

        String expectedMessage = "Season for date " + stayExpected.getCheckInDate() + " not found.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void given_missing_room_when_splitInvoice_then_roomNotFoundExceptionIsThrown() {
        // given
        RoomCategory category = RoomCategory.create(
                new RoomCategoryId("1"),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        Season winterSeason = Season.create(
                new SeasonId("1"),
                new SeasonName("Winter "),
                LocalDate.of(2021, 12, 1),
                LocalDate.of(2022, 1, 31)
        );

        List<Service> services = Arrays.asList(
                Service.create(new ServiceId("1"),
                        new ServiceName("TV"),
                        new Price(new BigDecimal("100").setScale(2, RoundingMode.CEILING))),
                Service.create(new ServiceId("2"),
                        new ServiceName("Breakfast"),
                        new Price(new BigDecimal("100").setScale(2, RoundingMode.CEILING)))
        );

        Guest guest = Guest.create(
                new GuestId("1"),
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

        Booking booking = Booking.create(
                LocalDate.of(2021, 12, 26),
                LocalDate.of(2021,12,29),
                new BookingId("1"),
                guest,
                services,
                2,
                1,
                "",
                "20211226001"
        );
        booking.addRoomCategory(category, 1);

        Map<Room, Boolean> rooms = Map.of(
                Room.create(new RoomName("101"), RoomStatus.FREE, category), false,
                Room.create(new RoomName("102"), RoomStatus.FREE, category), false,
                Room.create(new RoomName("103"), RoomStatus.FREE, category), false
        );

        List<String> roomNames = List.of(
                "101",
                "102",
                "103"
        );

        StayId stayIdExpected = new StayId("1");
        Stay stayExpected = Stay.create(stayIdExpected, booking, rooms);

        String actionExpected = "checkOut";

        Mockito.when(invoiceRepository.amountOfInvoicesByDate(LocalDate.now())).thenReturn(0);

        for (int i = 26; i <= 29; i++) {
            Mockito.when(seasonRepository.seasonByDate(LocalDate.of(2021, 12, i)))
                    .thenReturn(Optional.of(winterSeason));
        }

        Mockito.when(roomRepository.roomByName(new RoomName(roomNames.get(0)))).thenReturn(Optional.empty());

        // when ... then
        Exception exception = assertThrows(RoomNotFoundException.class, () -> {
            invoiceSplitService.splitInvoice(stayExpected, roomNames, actionExpected);
        });

        String expectedMessage = "Room " + roomNames.get(0) + " not found.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
