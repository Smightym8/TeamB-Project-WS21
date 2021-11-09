package at.fhv.se.hotel.domain.model.room;

import java.util.Objects;

public class Room {
    // eventually required by Hibernate
    private Long id;
    private String name;
    private String status;

    // eventually required by Hibernate
    private Room(){}

    public static Room create (String aName, String aStatus){
        return new Room(aName, aStatus);
    }

    private Room (String aName, String aStatus) {
        this.name = aName;
        this.status = aStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(id, room.id) && Objects.equals(name, room.name) && Objects.equals(status, room.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status);
    }
}
