package at.fhv.se.hotel.unit.domain;

import at.fhv.se.hotel.domain.model.booking.BookingId;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookingIdTests {

    @Test
    void given_3bookingids_2equals_1not_when_compare_then_2equals_1not() {
        // given
        String bookingIdStr1 = "1";
        String bookingIdStr2 = "2";
        BookingId bookingId1_1 = new BookingId(bookingIdStr1);
        BookingId bookingId1_2 = new BookingId(bookingIdStr1);
        BookingId bookingId2 = new BookingId(bookingIdStr2);

        // when .. then
        assertEquals(bookingId1_1, bookingId1_2);
        assertNotEquals(bookingId1_1, bookingId2);
        assertNotEquals(bookingId1_2, bookingId2);
    }
}
