package at.fhv.se.hotel.domain.model.roomcategory;

import at.fhv.se.hotel.domain.model.booking.Booking;

import java.util.List;
import java.util.Objects;

public class RoomCategory {
    // Required by hibernate
    private Long id;
    private RoomCategoryId roomCategoryId;
    private RoomCategoryName roomCategoryName;
    private Description description;

    // Required by hibernate
    protected RoomCategory() {
    }

    public static RoomCategory create (RoomCategoryId aRoomCategoryId, RoomCategoryName aRoomCategoryName,
                                       Description aDescription) {
        return new RoomCategory(aRoomCategoryId, aRoomCategoryName, aDescription);
    }

    private RoomCategory (RoomCategoryId aRoomCategoryId, RoomCategoryName aRoomCategoryName,
                         Description aDescription){
        this.roomCategoryId = aRoomCategoryId;
        this.roomCategoryName = aRoomCategoryName;
        this.description = aDescription;
    }

    public RoomCategoryId getRoomCategoryId() {
        return roomCategoryId;
    }

    public RoomCategoryName getRoomCategoryName() {
        return roomCategoryName;
    }

    public Description getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomCategory category = (RoomCategory) o;
        return Objects.equals(id, category.id)
                && Objects.equals(roomCategoryId, category.roomCategoryId)
                && Objects.equals(roomCategoryName, category.roomCategoryName)
                && Objects.equals(description, category.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomCategoryId, roomCategoryName, description);
    }
}
