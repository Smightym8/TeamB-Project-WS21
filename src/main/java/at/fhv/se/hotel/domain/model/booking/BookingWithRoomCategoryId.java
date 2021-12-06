package at.fhv.se.hotel.domain.model.booking;

import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;

import java.io.Serializable;
import java.util.Objects;

// TODO: Test
public class BookingWithRoomCategoryId implements Serializable {
    private Booking booking;
    private RoomCategory roomCategory;

    public BookingWithRoomCategoryId(Booking aBooking, RoomCategory aRoomCategory) {
        this.booking = aBooking;
        this.roomCategory = aRoomCategory;
    }

    // Required by hibernate
    private BookingWithRoomCategoryId() {}

    protected Booking getBooking() {
        return booking;
    }

    protected RoomCategory getRoomCategory() {
        return roomCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingWithRoomCategoryId that = (BookingWithRoomCategoryId) o;
        return Objects.equals(booking, that.booking) && Objects.equals(roomCategory, that.roomCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(booking, roomCategory);
    }
}
