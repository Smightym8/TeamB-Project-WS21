package at.fhv.se.hotel.domain.model.room;

import at.fhv.se.hotel.domain.Generated;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;

import java.util.Objects;

// TODO: Test
public class Room {
    // Required by hibernate
    @SuppressWarnings("unused")
    private Long id;
    private String name;
    private RoomStatus status;
    private RoomCategory roomCategory;

    // Required by hibernate
    @SuppressWarnings("unused")
    private Room(){}

    public static Room create (String aName, RoomStatus aStatus, RoomCategory aRoomCategory){
        return new Room(aName, aStatus, aRoomCategory);
    }

    private Room (String aName, RoomStatus aStatus, RoomCategory aRoomCategory) {
        this.name = aName;
        this.status = aStatus;
        this.roomCategory = aRoomCategory;
    }

    public String getName() {
        return name;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public RoomCategory getRoomCategory() {
        return roomCategory;
    }

    public void occupy() {
        this.status = RoomStatus.OCCUPIED;
    }

    public void clean() {
        this.status = RoomStatus.CLEANING;
    }

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(id, room.id) && Objects.equals(name, room.name) && status == room.status && Objects.equals(roomCategory, room.roomCategory);
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(id, name, status, roomCategory);
    }
}
