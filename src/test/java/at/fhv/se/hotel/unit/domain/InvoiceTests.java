package at.fhv.se.hotel.unit.domain;

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
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class InvoiceTests {
    @Test
    void given_invoicedetails_when_createinvoice_then_detailsequals() {
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
                        new Price(new BigDecimal("100"))),
                Service.create(new ServiceId("2"),
                        new ServiceName("Breakfast"),
                        new Price(new BigDecimal("100")))
        );

        Booking booking = Booking.create(
                LocalDate.of(2021, 8, 1),
                LocalDate.of(2021, 8, 10),
                new BookingId("1"),
                guest,
                services,
                2,
                1,
                "",
                "20210801001"
        );
        booking.addRoomCategory(category, 1);

        Season summerSeason = Season.create(
                new SeasonId("1"),
                new SeasonName("Summer"),
                LocalDate.of(2021, 6, 1),
                LocalDate.of(2021, 11, 30)
        );

        List<RoomCategoryPrice> categoryPricesExpected = List.of(
                RoomCategoryPrice.create(
                        new RoomCategoryPriceId("1"),
                        summerSeason,
                        category,
                        new BigDecimal("300")
                )
        );

        Map<Room, Boolean> rooms = Map.of(
                Room.create(new RoomName("101"), RoomStatus.FREE, category), false
        );

        List<Room> roomsForInvoice = new ArrayList<>(rooms.keySet());

        // when
        StayId stayIdExpected = new StayId("1");
        Stay stayExpected = Stay.create(stayIdExpected, booking, rooms);

        InvoiceId idExpected = new InvoiceId("42");
        String invoiceNumberExpected = "30112021001";
        int amountOfNightsExpected = 9;
        BigDecimal localTaxPerPersonExpected = new BigDecimal("0.76");
        BigDecimal localTaxTotalExpected = new BigDecimal("1.52");
        BigDecimal valueAddedTaxInPercentExpected = new BigDecimal("0.10");
        BigDecimal valueAddedTaxInEuroExpected = new BigDecimal("100");
        BigDecimal totalNetAmountBeforeDiscountExpected = new BigDecimal("200");
        BigDecimal discountInPercentExpected = new BigDecimal("0");
        BigDecimal discountInEuroExpected = new BigDecimal("0");
        BigDecimal totalNetAmountAfterDiscountExpected = new BigDecimal("200");
        BigDecimal totalNetAmountAfterLocalTaxExpected = new BigDecimal("201.52");
        BigDecimal totalGrossAmountExpected = new BigDecimal("301.52");

        // when
        Invoice invoiceActual = Invoice.create(
                idExpected,
                invoiceNumberExpected,
                stayExpected,
                categoryPricesExpected,
                services,
                roomsForInvoice,
                amountOfNightsExpected,
                localTaxPerPersonExpected,
                localTaxTotalExpected,
                valueAddedTaxInPercentExpected,
                valueAddedTaxInEuroExpected,
                totalNetAmountBeforeDiscountExpected,
                discountInPercentExpected,
                discountInEuroExpected,
                totalNetAmountAfterDiscountExpected,
                totalNetAmountAfterLocalTaxExpected,
                totalGrossAmountExpected
        );

        // then
        assertEquals(idExpected, invoiceActual.getInvoiceId());
        assertEquals(invoiceNumberExpected, invoiceActual.getInvoiceNumber());
        assertEquals(stayExpected, invoiceActual.getStay());
        assertEquals(amountOfNightsExpected, invoiceActual.getAmountOfNights());
        assertEquals(localTaxPerPersonExpected, invoiceActual.getLocalTaxPerPerson());
        assertEquals(localTaxTotalExpected, invoiceActual.getLocalTaxTotal());
        assertEquals(valueAddedTaxInPercentExpected, invoiceActual.getValueAddedTaxInPercent());
        assertEquals(valueAddedTaxInEuroExpected, invoiceActual.getValueAddedTaxInEuro());
        assertEquals(totalNetAmountBeforeDiscountExpected, invoiceActual.getTotalNetAmountBeforeDiscount());
        assertEquals(totalGrossAmountExpected, invoiceActual.getTotalGrossAmount());
    }

    @Test
    void given_3invoiceids_2equals_1not_when_compare_then_2equals_1not() {
        // given
        InvoiceId id0_1 = new InvoiceId("0");
        InvoiceId id0_2 = new InvoiceId("0");
        InvoiceId id1 = new InvoiceId("1");

        // when...then
        assertEquals(id0_1, id0_2, "both ids should be equal");
        assertNotEquals(id0_1, id1, "the ids should not be equal");
        assertNotEquals(id0_2, id1, "the ids should not be equal");
    }

    @Test
    void given_id_when_create_id_then_return_equals() {
        // given
        String invoiceIdStr = "123";

        // when
        InvoiceId invoiceId = new InvoiceId(invoiceIdStr);

        // then
        assertEquals(invoiceIdStr, invoiceId.id());
    }
}
