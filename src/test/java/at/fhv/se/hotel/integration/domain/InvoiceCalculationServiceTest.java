package at.fhv.se.hotel.integration.domain;

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
import at.fhv.se.hotel.domain.repository.InvoiceRepository;
import at.fhv.se.hotel.domain.repository.RoomCategoryPriceRepository;
import at.fhv.se.hotel.domain.repository.SeasonRepository;
import at.fhv.se.hotel.domain.services.api.InvoiceCalculationService;
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
public class InvoiceCalculationServiceTest {
    @Autowired
    InvoiceCalculationService invoiceCalculationService;

    @MockBean
    RoomCategoryPriceRepository roomCategoryPriceRepository;

    @MockBean
    InvoiceRepository invoiceRepository;

    @MockBean
    SeasonRepository seasonRepository;

    @Test
    void given_invoicedetails_when_calculate_then_returnexpectedamount() {
        // given
        Guest guest = Guest.create(new GuestId("1"),
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

        RoomCategory category = RoomCategory.create(new RoomCategoryId("1"),
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
                LocalDate.of(2021, 11, 26),
                LocalDate.of(2021,11,29),
                new BookingId("1"),
                guest,
                services,
                2,
                1,
                ""
        );
        booking.addRoomCategory(category, 1);

        Map<Room, Boolean> rooms = Map.of(
                Room.create(new RoomName("101"), RoomStatus.FREE, category), false
        );

        Season summerSeason = Season.create(
                new SeasonId("1"),
                new SeasonName("Summer"),
                LocalDate.of(2021, 6, 1),
                LocalDate.of(2021, 11, 30)
        );

        RoomCategoryPrice price = RoomCategoryPrice.create(
                new RoomCategoryPriceId("1"),
                summerSeason,
                category,
                new BigDecimal("600").setScale(2, RoundingMode.CEILING)
        );

        Stay stayExpected = Stay.create(booking, rooms);

        String invoiceNumberExpected = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001";
        int amountOfNightsExpected = 3;
        BigDecimal localTaxPerPersonExpected = new BigDecimal("0.76").setScale(2, RoundingMode.CEILING);
        BigDecimal localTaxTotalExpected = new BigDecimal("1.52").setScale(2, RoundingMode.CEILING);
        BigDecimal valueAddedTaxInPercentExpected = new BigDecimal("0.10").setScale(2, RoundingMode.CEILING);
        BigDecimal totalNetAmountBeforeDiscountExpected = new BigDecimal("2400").setScale(2, RoundingMode.CEILING);
        BigDecimal totalNetAmountAfterDiscountExpected = new BigDecimal("2400").setScale(2, RoundingMode.CEILING);
        BigDecimal totalNetAmountAfterLocalTaxExpected = new BigDecimal("2401.52").setScale(2, RoundingMode.CEILING);
        BigDecimal valueAddedTaxInEuroExpected = new BigDecimal("240.00").setScale(2, RoundingMode.CEILING);
        BigDecimal totalGrossAmountExpected = new BigDecimal("2641.52").setScale(2, RoundingMode.CEILING);

        // when
        Mockito.when(invoiceRepository.invoicesByDate(LocalDate.now())).thenReturn(Collections.emptyList());

        Mockito.when(invoiceRepository.nextIdentity()).thenReturn(
            new InvoiceId(UUID.randomUUID().toString().toUpperCase())
        );

        Mockito.when(seasonRepository.seasonByDate(LocalDate.of(2021, 11, 26)))
                .thenReturn(Optional.of(summerSeason));
        Mockito.when(seasonRepository.seasonByDate(LocalDate.of(2021, 11, 27)))
                .thenReturn(Optional.of(summerSeason));
        Mockito.when(seasonRepository.seasonByDate(LocalDate.of(2021, 11, 28)))
                .thenReturn(Optional.of(summerSeason));
        Mockito.when(seasonRepository.seasonByDate(LocalDate.of(2021, 11, 29)))
                .thenReturn(Optional.of(summerSeason));

        Mockito.when(roomCategoryPriceRepository.priceBySeasonAndCategory(summerSeason.getSeasonId(), category.getRoomCategoryId()))
                .thenReturn(java.util.Optional.of(price));

        Invoice invoice = invoiceCalculationService.calculateInvoice(stayExpected);

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
}
