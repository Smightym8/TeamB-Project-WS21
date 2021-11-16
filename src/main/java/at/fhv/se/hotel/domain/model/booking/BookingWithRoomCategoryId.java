package at.fhv.se.hotel.domain.model.booking;

import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;

import java.util.Objects;

public class BookingWithRoomCategoryId {
    private BookingId bookingId;
    private RoomCategoryId roomCategoryId;

    public BookingWithRoomCategoryId(BookingId aBookingId, RoomCategoryId aRoomCategoryId) {
        this.bookingId = aBookingId;
        this.roomCategoryId = aRoomCategoryId;
    }

    // Required by hibernate
    private BookingWithRoomCategoryId() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingWithRoomCategoryId that = (BookingWithRoomCategoryId) o;
        return Objects.equals(bookingId, that.bookingId) && Objects.equals(roomCategoryId, that.roomCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId, roomCategoryId);
    }
}
