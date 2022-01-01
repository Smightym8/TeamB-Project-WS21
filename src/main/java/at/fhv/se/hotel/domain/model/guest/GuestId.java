package at.fhv.se.hotel.domain.model.guest;

import at.fhv.se.hotel.domain.Generated;

import java.util.Objects;

// TODO: Test
public class GuestId {
    private String id;

    // Required by hibernate
    @SuppressWarnings("unused")
    private GuestId() {
    }

    public GuestId(String id){
        this.id = id;
    }

    public String id(){
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
