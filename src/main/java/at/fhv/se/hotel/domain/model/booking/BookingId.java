package at.fhv.se.hotel.domain.model.booking;

import java.util.Objects;

public class BookingId {
    private String id;

    // Required by hibernate
    private BookingId() {}

    public BookingId(String id){
        this.id = id;
    }

    public String id(){
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingId bookingId = (BookingId) o;
        return Objects.equals(id, bookingId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
