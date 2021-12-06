package at.fhv.se.hotel.domain.model.roomcategory;

import java.util.Objects;

// TODO: Test
public class RoomCategoryId {
    private String id;

    // Required by hibernate
    private RoomCategoryId() {
    }

    public RoomCategoryId(String id){
        this.id = id;
    }

    public String id(){
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomCategoryId that = (RoomCategoryId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
