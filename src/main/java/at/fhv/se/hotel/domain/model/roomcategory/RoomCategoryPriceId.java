package at.fhv.se.hotel.domain.model.roomcategory;

import at.fhv.se.hotel.domain.Generated;

import java.util.Objects;

// TODO: Test
public class RoomCategoryPriceId {
    private String id;

    public RoomCategoryPriceId (String id) {
        this.id = id;
    }

    // Required by Hibernate
    private RoomCategoryPriceId (){}

    public String id() {
        return this.id;
    }

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomCategoryPriceId that = (RoomCategoryPriceId) o;
        return Objects.equals(id, that.id);
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
