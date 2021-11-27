package at.fhv.se.hotel.unit.domain;

import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;
import at.fhv.se.hotel.domain.model.guest.*;
import at.fhv.se.hotel.domain.model.invoice.Invoice;
import at.fhv.se.hotel.domain.model.invoice.InvoiceId;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomStatus;
import at.fhv.se.hotel.domain.model.roomcategory.Description;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryName;
import at.fhv.se.hotel.domain.model.service.Price;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.service.ServiceId;
import at.fhv.se.hotel.domain.model.service.ServiceName;
import at.fhv.se.hotel.domain.model.stay.Stay;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class InvoiceTest {

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
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                new BookingId("1"),
                guest,
                services
        );
        booking.addRoomCategory(category, 1);

        List<Room> rooms = List.of(
                Room.create("101", RoomStatus.FREE, category)
        );

        // when
        Stay stayExpected = Stay.create(booking, rooms);

        InvoiceId idExpected = new InvoiceId("42");
        Invoice invoiceExpected = Invoice.create(idExpected, stayExpected);

        // then
        assertEquals(idExpected, invoiceExpected.getInvoiceId());
        assertEquals(stayExpected, invoiceExpected.getStay());
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
                LocalDate.of(2021, 11, 26),
                LocalDate.of(2021,11,29),
                new BookingId("1"),
                guest,
                services
        );
        booking.addRoomCategory(category, 1);

        List<Room> rooms = List.of(
                Room.create("101", RoomStatus.FREE, category)
        );
        Stay stayExpected = Stay.create(booking, rooms);

        InvoiceId id = new InvoiceId("42");
        BigDecimal totalAmountServices = new BigDecimal("200");
        BigDecimal totalAmountNights = new BigDecimal("1800");

        BigDecimal totalAmountExpected = totalAmountNights.add(totalAmountServices); // 2.000

        // when
        Invoice invoice = Invoice.create(id, stayExpected);

        // then
        assertEquals(totalAmountExpected, invoice.getTotalAmount());
    }
    
}
