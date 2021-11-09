package at.fhv.se.hotel.domain.model.room;

import java.util.Objects;

public class Room {
    // eventually required by Hibernate
    private Long id;
    private String name;
    private RoomStatus status;

    // eventually required by Hibernate
    private Room(){}

    public static Room create (String aName, RoomStatus aStatus){
        return new Room(aName, aStatus);
    }

    private Room (String aName, RoomStatus aStatus) {
        this.name = aName;
        this.status = aStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(id, room.id) && Objects.equals(name, room.name) && status == room.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status);
    }
}
