package at.fhv.se.hotel.unit.domain;

import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;
import at.fhv.se.hotel.domain.model.booking.BookingWithRoomCategory;
import at.fhv.se.hotel.domain.model.booking.BookingWithRoomCategoryId;
import at.fhv.se.hotel.domain.model.guest.*;
import at.fhv.se.hotel.domain.model.roomcategory.Description;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryName;
import at.fhv.se.hotel.domain.model.service.Price;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.service.ServiceId;
import at.fhv.se.hotel.domain.model.service.ServiceName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookingWithRoomCategoryTests {

    @Test
    void given_booking_and_roomcategory_when_create_then_return_equal_booking_and_roomcategory() {

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
                Collections.emptyList()
        );

        Service service = Service.create(
                new ServiceId("1"),
                new ServiceName("TV"),
                new Price(new BigDecimal("100"))
        );

        Booking booking = Booking.create(
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                new BookingId("1"),
                guest,
                List.of(service),
                2,
                0,
                "Extra pillow"
        );

        RoomCategory roomCategory = RoomCategory.create(
                new RoomCategoryId("1"),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        BookingWithRoomCategoryId brcId = new BookingWithRoomCategoryId(
                booking,
                roomCategory
        );

        int amount = 1;

        // when
        BookingWithRoomCategory brc = BookingWithRoomCategory.create(
                brcId,
                amount
        );

        // then
        assertEquals(brcId, brc.getBookingWithRoomCategoryId());
        assertEquals(amount, brc.getAmount());
        assertEquals(booking, brc.getBooking());
        assertEquals(roomCategory, brc.getRoomCategory());
    }
}
