package at.fhv.se.hotel.integration.domain;

import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;
import at.fhv.se.hotel.domain.model.guest.*;
import at.fhv.se.hotel.domain.model.invoice.Invoice;
import at.fhv.se.hotel.domain.model.invoice.InvoiceId;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomStatus;
import at.fhv.se.hotel.domain.model.roomcategory.*;
import at.fhv.se.hotel.domain.model.service.Price;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.service.ServiceId;
import at.fhv.se.hotel.domain.model.service.ServiceName;
import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.repository.InvoiceRepository;
import at.fhv.se.hotel.domain.repository.RoomCategoryPriceRepository;
import at.fhv.se.hotel.domain.repository.RoomRepository;
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

    @Test
    void given_invoicedetails_when_splitInvoice_then_returnexpectedamount() {
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
                ""
        );
        booking.addRoomCategory(category, 1);

        Map<Room, Boolean> rooms = Map.of(
                Room.create("101", RoomStatus.FREE, category), false,
                Room.create("102", RoomStatus.FREE, category), false,
                Room.create("103", RoomStatus.FREE, category), false
        );

        RoomCategoryPrice price = RoomCategoryPrice.create(
                new RoomCategoryPriceId("1"),
                Season.WINTER,
                category,
                new BigDecimal("600").setScale(2, RoundingMode.CEILING)
        );

        List<Room> roomsExpected = new ArrayList(rooms.keySet());

        List<String> selectedRooms = List.of("101");

        Stay stayExpected = Stay.create(booking, rooms);

        String invoiceNumberExpected = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001";
        int amountOfNightsExpected = 3;
        BigDecimal localTaxPerPersonExpected = new BigDecimal("0.76").setScale(2, RoundingMode.CEILING);
        BigDecimal localTaxTotalExpected = new BigDecimal("1.52").setScale(2, RoundingMode.CEILING);
        BigDecimal valueAddedTaxInPercentExpected = new BigDecimal("0.10").setScale(2, RoundingMode.CEILING);
        BigDecimal totalNetAmountExpected = new BigDecimal("2001.52").setScale(2, RoundingMode.CEILING);
        BigDecimal valueAddedTaxInEuroExpected = new BigDecimal("200.00").setScale(2, RoundingMode.CEILING);
        BigDecimal totalGrossAmountExpected = new BigDecimal("2201.52").setScale(2, RoundingMode.CEILING);

        InvoiceId invoiceId = new InvoiceId("1");

        // when
        Mockito.when(invoiceRepository.invoicesByDate(LocalDate.now())).thenReturn(Collections.emptyList());

        Mockito.when(roomRepository.roomByName("101")).thenReturn(java.util.Optional.of(roomsExpected.get(0)));

        Mockito.when(roomCategoryPriceRepository.priceBySeasonAndCategory(Season.WINTER, roomsExpected.get(0).getRoomCategory().getRoomCategoryId()))
                .thenReturn(java.util.Optional.of(price));

        Mockito.when(invoiceRepository.nextIdentity()).thenReturn(invoiceId);

        Invoice invoice = invoiceSplitService.splitInvoice(stayExpected, selectedRooms);

        // then
        assertEquals(invoiceNumberExpected, invoice.getInvoiceNumber());
        assertEquals(amountOfNightsExpected, invoice.getAmountOfNights());
        assertEquals(localTaxPerPersonExpected, invoice.getLocalTaxPerPerson());
        assertEquals(localTaxTotalExpected, invoice.getLocalTaxTotal());
        assertEquals(valueAddedTaxInPercentExpected, invoice.getValueAddedTaxInPercent());
        assertEquals(totalNetAmountExpected, invoice.getTotalNetAmount());
        assertEquals(valueAddedTaxInEuroExpected, invoice.getValueAddedTaxInEuro());
        assertEquals(totalGrossAmountExpected, invoice.getTotalGrossAmount());
    }

    @Test
    void given_2rooms_when_splitInvoice_then_returnexpectedamount() {
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
                ""
        );
        booking.addRoomCategory(category, 1);

        Map<Room, Boolean> rooms = Map.of(
                Room.create("101", RoomStatus.FREE, category), false,
                Room.create("102", RoomStatus.FREE, category), false,
                Room.create("103", RoomStatus.FREE, category), false
        );

        RoomCategoryPrice price = RoomCategoryPrice.create(
                new RoomCategoryPriceId("1"),
                Season.WINTER,
                category,
                new BigDecimal("600").setScale(2, RoundingMode.CEILING)
        );

        List<Room> roomsExpected = new ArrayList(rooms.keySet());

        List<String> selectedRooms = List.of("101", "102");

        Stay stayExpected = Stay.create(booking, rooms);

        String invoiceNumberExpected = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001";
        int amountOfNightsExpected = 3;
        BigDecimal localTaxPerPersonExpected = new BigDecimal("0.76").setScale(2, RoundingMode.CEILING);
        BigDecimal localTaxTotalExpected = new BigDecimal("1.52").setScale(2, RoundingMode.CEILING);
        BigDecimal valueAddedTaxInPercentExpected = new BigDecimal("0.10").setScale(2, RoundingMode.CEILING);
        BigDecimal totalNetAmountExpected = new BigDecimal("4001.52").setScale(2, RoundingMode.CEILING);
        BigDecimal valueAddedTaxInEuroExpected = new BigDecimal("400.00").setScale(2, RoundingMode.CEILING);
        BigDecimal totalGrossAmountExpected = new BigDecimal("4401.52").setScale(2, RoundingMode.CEILING);

        InvoiceId invoiceId = new InvoiceId("1");

        // when
        Mockito.when(invoiceRepository.invoicesByDate(LocalDate.now())).thenReturn(Collections.emptyList());

        Mockito.when(roomRepository.roomByName("101")).thenReturn(java.util.Optional.of(roomsExpected.get(0)));
        Mockito.when(roomRepository.roomByName("102")).thenReturn(java.util.Optional.of(roomsExpected.get(1)));

        Mockito.when(roomCategoryPriceRepository.priceBySeasonAndCategory(Season.WINTER, roomsExpected.get(0).getRoomCategory().getRoomCategoryId()))
                .thenReturn(java.util.Optional.of(price));

        Mockito.when(invoiceRepository.nextIdentity()).thenReturn(invoiceId);

        Invoice invoice = invoiceSplitService.splitInvoice(stayExpected, selectedRooms);

        // then
        assertEquals(invoiceNumberExpected, invoice.getInvoiceNumber());
        assertEquals(amountOfNightsExpected, invoice.getAmountOfNights());
        assertEquals(localTaxPerPersonExpected, invoice.getLocalTaxPerPerson());
        assertEquals(localTaxTotalExpected, invoice.getLocalTaxTotal());
        assertEquals(valueAddedTaxInPercentExpected, invoice.getValueAddedTaxInPercent());
        assertEquals(totalNetAmountExpected, invoice.getTotalNetAmount());
        assertEquals(valueAddedTaxInEuroExpected, invoice.getValueAddedTaxInEuro());
        assertEquals(totalGrossAmountExpected, invoice.getTotalGrossAmount());
    }
}
