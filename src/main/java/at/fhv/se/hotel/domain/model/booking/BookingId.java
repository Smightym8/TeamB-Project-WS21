package at.fhv.se.hotel.domain.model.booking;

import at.fhv.se.hotel.domain.Generated;

import java.util.Objects;

/**
 * This class is a value object for the booking which contains the id
 */
public class BookingId {
    private String id;

    // Required by hibernate
    @SuppressWarnings("unused")
    @Generated
    private BookingId() {
    }

    public BookingId(String id) {
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
        BookingId bookingId = (BookingId) o;
        return Objects.equals(id, bookingId.id);
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
