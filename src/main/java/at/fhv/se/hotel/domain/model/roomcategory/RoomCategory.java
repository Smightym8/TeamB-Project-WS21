package at.fhv.se.hotel.domain.model.roomcategory;

import at.fhv.se.hotel.domain.Generated;

import javax.xml.bind.annotation.XmlElement;
import java.util.Objects;

public class RoomCategory {
    // Required by hibernate
    @SuppressWarnings("unused")
    private Long id;
    private RoomCategoryId roomCategoryId;
    private RoomCategoryName roomCategoryName;
    private Description description;

    // Required by hibernate
    @SuppressWarnings("unused")
    @Generated
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

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomCategory that = (RoomCategory) o;
        return Objects.equals(id, that.id) && Objects.equals(roomCategoryId, that.roomCategoryId) && Objects.equals(roomCategoryName, that.roomCategoryName) && Objects.equals(description, that.description);
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(id, roomCategoryId, roomCategoryName, description);
    }
}
