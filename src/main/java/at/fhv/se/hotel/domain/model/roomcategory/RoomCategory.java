package at.fhv.se.hotel.domain.model.roomcategory;

import at.fhv.se.hotel.domain.model.booking.Booking;

import java.util.List;

public class RoomCategory {
    // Required by hibernate
    private Long id;
    private RoomCategoryId roomCategoryId;
    private RoomCategoryName roomCategoryName;
    private Description description;
    private List<Booking> bookings;

    public static RoomCategory create (RoomCategoryId aRoomCategoryId, RoomCategoryName aRoomCategoryName,
                                       Description aDescription, List<Booking> aBookings) {
        return new RoomCategory(aRoomCategoryId, aRoomCategoryName, aDescription, aBookings);
    }

    private RoomCategory (RoomCategoryId aRoomCategoryId, RoomCategoryName aRoomCategoryName,
                         Description aDescription, List<Booking> aBookings){
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
