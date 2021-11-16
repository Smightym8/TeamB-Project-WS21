package at.fhv.se.hotel.domain.model.booking;

import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;

import java.util.Objects;

public class BookingWithRoomCategory {
    // Required by hibernate
    private Long id;
    private BookingWithRoomCategoryId bookingWithRoomCategoryId;
    private Booking booking;
    private RoomCategory roomCategory;
    private int amount;

    // Required by hibernate
    private BookingWithRoomCategory() {}

    public static BookingWithRoomCategory create(BookingWithRoomCategoryId aBookingWithRoomCategoryId,
                                                 Booking aBooking,
                                                 RoomCategory aRoomCategory,
                                                 int anAmount) {
        return new BookingWithRoomCategory(aBookingWithRoomCategoryId,
                aBooking, aRoomCategory, anAmount);
    }

    public BookingWithRoomCategory(BookingWithRoomCategoryId aBookingWithRoomCategoryId,
                                   Booking aBooking,
                                   RoomCategory aRoomCategory,
                                   int anAmount) {
        this.bookingWithRoomCategoryId = aBookingWithRoomCategoryId;
        this.booking = aBooking;
        this.roomCategory = aRoomCategory;
        this.amount = anAmount;
    }

    public BookingWithRoomCategoryId getBookingWithRoomCategoryId() {
        return bookingWithRoomCategoryId;
    }

    public Booking getBooking() {
        return booking;
    }

    public RoomCategory getRoomCategory() {
        return roomCategory;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingWithRoomCategory that = (BookingWithRoomCategory) o;
        return amount == that.amount
                && Objects.equals(id, that.id)
                && Objects.equals(bookingWithRoomCategoryId, that.bookingWithRoomCategoryId)
                && Objects.equals(booking, that.booking)
                && Objects.equals(roomCategory, that.roomCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookingWithRoomCategoryId,
                booking, roomCategory, amount);
    }
}
