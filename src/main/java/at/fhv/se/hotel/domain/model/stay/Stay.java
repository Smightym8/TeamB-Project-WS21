package at.fhv.se.hotel.domain.model.stay;

import at.fhv.se.hotel.domain.Generated;
import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.guest.Guest;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.service.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Stay {
    // Required by hibernate
    @SuppressWarnings("unused")
    private Long id;
    private StayId stayId;
    private Map<Room, Boolean> rooms;
    private Booking booking;
    private boolean isActive;

    // Required by hibernate
    @SuppressWarnings("unused")
    @Generated
    protected Stay() {
    }

    public static Stay create(StayId aStayId, Booking aBooking, Map<Room, Boolean> aRooms) {
        return new Stay(aStayId, aBooking, aRooms);
    }

    private Stay(StayId aStayId, Booking aBooking, Map<Room, Boolean> aRooms) {
        this.stayId = aStayId;
        this.booking = aBooking;
        this.rooms = aRooms;
        this.isActive = true;
    }

    public StayId getStayId() {
        return stayId;
    }

    public Map<Room, Boolean> getRooms() {
        return rooms;
    }

    public Booking getBooking() {
        return booking;
    }

    public Guest getGuest() {
        return booking.getGuest();
    }

    public LocalDate getCheckInDate() {
        return booking.getCheckInDate();
    }

    public LocalDate getCheckOutDate() {
        return booking.getCheckOutDate();
    }

    public List<Service> getServices() {
        return booking.getServices();
    }

    public boolean isActive() {
        return isActive;
    }

    public void deactivate() {
        isActive = false;
    }

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stay stay = (Stay) o;
        return isActive == stay.isActive && Objects.equals(id, stay.id) && Objects.equals(stayId, stay.stayId) && Objects.equals(rooms, stay.rooms) && Objects.equals(booking, stay.booking);
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(id, stayId, rooms, booking, isActive);
    }
}
