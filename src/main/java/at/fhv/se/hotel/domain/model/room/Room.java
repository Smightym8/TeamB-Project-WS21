package at.fhv.se.hotel.domain.model.room;

import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;

import java.util.Objects;

public class Room {
    // eventually required by Hibernate
    private Long id;
    private String name;
    private RoomStatus status;
    private RoomCategory roomCategory;

    // eventually required by Hibernate
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(id, room.id) && Objects.equals(name, room.name) && status == room.status && Objects.equals(roomCategory, room.roomCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status, roomCategory);
    }
}
