package at.fhv.se.hotel.domain.models.RoomCategory;

import at.fhv.se.hotel.domain.models.Booking.Booking;

import java.util.List;

public class RoomCategory {
    private RoomCategoryId roomCategoryId;
    private RoomCategoryName roomCategoryName;
    private Description description;
    private List<Booking> bookings;

    public RoomCategory (RoomCategoryId aRoomCategoryId, RoomCategoryName aRoomCategoryName, Description aDescription, List<Booking> aBookings){
        this.roomCategoryId = aRoomCategoryId;
        this.roomCategoryName = aRoomCategoryName;
        this.description = aDescription;
        this.bookings = aBookings;
    }

    public RoomCategoryId getRoomCategoryId() {
        return roomCategoryId;
    }

    public void setRoomCategoryId(RoomCategoryId roomCategoryId) {
        this.roomCategoryId = roomCategoryId;
    }

    public RoomCategoryName getRoomCategoryName() {
        return roomCategoryName;
    }

    public void setRoomCategoryName(RoomCategoryName roomCategoryName) {
        this.roomCategoryName = roomCategoryName;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
