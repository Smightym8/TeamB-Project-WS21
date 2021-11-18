package at.fhv.se.hotel.domain.model.booking;

import at.fhv.se.hotel.domain.model.guest.Guest;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.service.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Booking {
    // Required by hibernate
    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private boolean isActive;
    private BookingId bookingId;
    private Guest guest;
    private List<BookingWithRoomCategory> roomCategories;
    private List<Service> services;

    // Required by hibernate
    private Booking() {}

    public static Booking create(LocalDate aCheckInDate, LocalDate aCheckOutDate,
                                 BookingId aBookingId, Guest aGuest,
                                 List<Service> aServices) {

        return new Booking(aCheckInDate, aCheckOutDate, aBookingId, aGuest, aServices);
    }

    private Booking(LocalDate aCheckInDate, LocalDate aCheckOutDate,
                    BookingId aBookingId, Guest aGuest,
                    List<Service> aServices) {
        this.checkInDate = aCheckInDate;
        this.checkOutDate = aCheckOutDate;
        this.isActive = true;
        this.bookingId = aBookingId;
        this.guest = aGuest;
        this.services = aServices;
        this.roomCategories = new ArrayList<>();
    }

    public void addRoomCategory(RoomCategory aRoomCategory, int anAmount) {
        BookingWithRoomCategory bookingWithRoomCategory = BookingWithRoomCategory.create(
                new BookingWithRoomCategoryId(this, aRoomCategory),
                 anAmount
        );

        this.roomCategories.add(bookingWithRoomCategory);
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public BookingId getBookingId() {
        return bookingId;
    }

    public Guest getGuest() {
        return guest;
    }

    public List<BookingWithRoomCategory> getRoomCategories() {
        return roomCategories;
    }

    public List<Service> getServices() {
        return services;
    }

    public void deactivate() {
        isActive = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id)
                && Objects.equals(checkInDate, booking.checkInDate)
                && Objects.equals(checkOutDate, booking.checkOutDate)
                && Objects.equals(bookingId, booking.bookingId)
                && Objects.equals(guest, booking.guest)
                && Objects.equals(roomCategories, booking.roomCategories)
                && Objects.equals(services, booking.services);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, checkInDate, checkOutDate,
                bookingId, guest, roomCategories, services);
    }
}
