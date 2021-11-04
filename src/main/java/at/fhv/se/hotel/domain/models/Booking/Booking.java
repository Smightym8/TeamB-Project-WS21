package at.fhv.se.hotel.domain.models.Booking;

import at.fhv.se.hotel.domain.models.Guest.Guest;
import at.fhv.se.hotel.domain.models.RoomCategory.RoomCategory;
import at.fhv.se.hotel.domain.models.Service.Service;

import java.time.LocalDate;
import java.util.List;

public class Booking {
    private LocalDate fromDate;
    private LocalDate toDate;
    private BookingId bookingId;
    private Guest guest;
    private List<RoomCategory> roomCategories;
    private List<Service> services;

    public Booking(LocalDate aFromDate, LocalDate aToDate, BookingId aBookingId, Guest aGuest, List<RoomCategory> aRoomCategories, List<Service> aServices) {
        this.fromDate = aFromDate;
        this.toDate = aToDate;
        this.bookingId = aBookingId;
        this.guest = aGuest;
        this.roomCategories = aRoomCategories;
        this.services = aServices;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public BookingId getBookingId() {
        return bookingId;
    }

    public void setBookingId(BookingId bookingId) {
        this.bookingId = bookingId;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public List<RoomCategory> getRoomCategories() {
        return roomCategories;
    }

    public void setRoomCategories(List<RoomCategory> roomCategories) {
        this.roomCategories = roomCategories;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
