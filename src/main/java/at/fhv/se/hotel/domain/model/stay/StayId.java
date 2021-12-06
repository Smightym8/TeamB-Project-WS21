package at.fhv.se.hotel.domain.model.stay;

import java.util.Objects;

// TODO: Test
public class StayId {
    private String id;

    // Required by hibernate
    public StayId() {
    }

    public StayId(String id) {
        this.id = id;
    }

    public String id() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StayId stayId = (StayId) o;
        return Objects.equals(id, stayId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
