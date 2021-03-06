package at.fhv.se.hotel.domain.model.booking;

import at.fhv.se.hotel.domain.Generated;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class is a value object for BookingWithRoomCategory which contains the id
 */
public class BookingWithRoomCategoryId implements Serializable {
    private Booking booking;
    private RoomCategory roomCategory;

    // Required by hibernate
    @SuppressWarnings("unused")
    @Generated
    private BookingWithRoomCategoryId() {
    }

    public BookingWithRoomCategoryId(Booking aBooking, RoomCategory aRoomCategory) {
        this.booking = aBooking;
        this.roomCategory = aRoomCategory;
    }

    protected Booking getBooking() {
        return booking;
    }

    protected RoomCategory getRoomCategory() {
        return roomCategory;
    }

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingWithRoomCategoryId that = (BookingWithRoomCategoryId) o;
        return Objects.equals(booking, that.booking) && Objects.equals(roomCategory, that.roomCategory);
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(booking, roomCategory);
    }
}
