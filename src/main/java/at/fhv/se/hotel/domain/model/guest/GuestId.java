package at.fhv.se.hotel.domain.model.guest;

import java.util.Objects;

public class GuestId {
    private String id;

    public GuestId(String id){
        this.id = id;
    }

    //Required by hibernate
    private GuestId() {
    }

    public String id(){
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuestId guestId = (GuestId) o;
        return Objects.equals(id, guestId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
