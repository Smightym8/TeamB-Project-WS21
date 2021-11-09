package at.fhv.se.hotel.domain.model.roomcategory;

import java.util.Objects;

public class RoomCategoryName {
    private String name;

    // Required by hibernate
    private RoomCategoryName() {
    }

    public RoomCategoryName(String aName){
        this.name = aName;
    }

    public String name() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomCategoryName that = (RoomCategoryName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}