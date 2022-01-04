package at.fhv.se.hotel.domain.model.room;

import at.fhv.se.hotel.domain.Generated;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;

import java.util.Objects;

public class Room {
    // Required by hibernate
    @SuppressWarnings("unused")
    private Long id;
    private RoomName roomName;
    private RoomStatus status;
    private RoomCategory roomCategory;

    // Required by hibernate
    @SuppressWarnings("unused")
    protected Room(){}

    public static Room create (RoomName aName, RoomStatus aStatus, RoomCategory aRoomCategory){
        return new Room(aName, aStatus, aRoomCategory);
    }

    private Room (RoomName aName, RoomStatus aStatus, RoomCategory aRoomCategory) {
        this.roomName = aName;
        this.status = aStatus;
        this.roomCategory = aRoomCategory;
    }

    public RoomName getName() {
        return roomName;
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

    public void changeStatus(RoomStatus aStatus) {
        this.status = aStatus;
    }

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(id, room.id) && Objects.equals(roomName, room.roomName) && status == room.status && Objects.equals(roomCategory, room.roomCategory);
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(id, roomName, status, roomCategory);
    }
}
