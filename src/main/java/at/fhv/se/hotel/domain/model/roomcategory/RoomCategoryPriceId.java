package at.fhv.se.hotel.domain.model.roomcategory;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomCategoryPriceId that = (RoomCategoryPriceId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
