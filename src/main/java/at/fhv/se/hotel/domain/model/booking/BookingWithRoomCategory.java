package at.fhv.se.hotel.domain.model.booking;

import at.fhv.se.hotel.domain.Generated;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;

import java.util.Objects;

/**
 * This class acts as relationship between Booking and RoomCategory.
 * It is used to create a many to many relationship with the additional field amount.
 */
public class BookingWithRoomCategory {
    // Required by hibernate
    @SuppressWarnings("unused")
    private Long id;
    private BookingWithRoomCategoryId bookingWithRoomCategoryId;
    private int amount;

    // Required by hibernate
    @SuppressWarnings("unused")
    @Generated
    private BookingWithRoomCategory() {
    }

    public static BookingWithRoomCategory create(BookingWithRoomCategoryId aBookingWithRoomCategoryId,
                                                 int anAmount) {
        return new BookingWithRoomCategory(aBookingWithRoomCategoryId, anAmount);
    }

    public BookingWithRoomCategory(BookingWithRoomCategoryId aBookingWithRoomCategoryId,
                                   int anAmount) {
        this.bookingWithRoomCategoryId = aBookingWithRoomCategoryId;
        this.amount = anAmount;
    }

    public BookingWithRoomCategoryId getBookingWithRoomCategoryId() {
        return bookingWithRoomCategoryId;
    }

    public RoomCategory getRoomCategory() {
        return bookingWithRoomCategoryId.getRoomCategory();
    }

    public Booking getBooking() {
        return bookingWithRoomCategoryId.getBooking();
    }

    public int getAmount() {
        return amount;
    }

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingWithRoomCategory that = (BookingWithRoomCategory) o;
        return amount == that.amount && Objects.equals(id, that.id) && Objects.equals(bookingWithRoomCategoryId, that.bookingWithRoomCategoryId);
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(id, bookingWithRoomCategoryId, amount);
    }
}
