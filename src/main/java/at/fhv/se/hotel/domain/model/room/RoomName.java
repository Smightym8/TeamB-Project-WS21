package at.fhv.se.hotel.domain.model.room;

import at.fhv.se.hotel.domain.Generated;

import java.util.Objects;

public class RoomName {
    private String name;

    // Required by hibernate
    @SuppressWarnings("unused")
    @Generated
    private RoomName() {
    }

    public RoomName(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomName roomName = (RoomName) o;
        return Objects.equals(name, roomName.name);
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
