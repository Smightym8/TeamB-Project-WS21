package at.fhv.se.hotel.domain.model.guest;

import at.fhv.se.hotel.domain.Generated;

import java.util.Objects;

/**
 * This class is a value object for the guest which contains the id
 */
public class GuestId {
    private String id;

    // Required by hibernate
    @SuppressWarnings("unused")
    @Generated
    private GuestId() {
    }

    public GuestId(String id) {
        this.id = id;
    }

    public String id() {
        return this.id;
    }

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuestId guestId = (GuestId) o;
        return Objects.equals(id, guestId.id);
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
