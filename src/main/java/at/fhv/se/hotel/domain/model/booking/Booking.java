package at.fhv.se.hotel.domain.model.booking;

import at.fhv.se.hotel.domain.Generated;
import at.fhv.se.hotel.domain.model.guest.Guest;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.service.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Booking {
    // Required by hibernate
    @SuppressWarnings("unused")
    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private boolean isActive;
    private BookingId bookingId;
    private Guest guest;
    private List<BookingWithRoomCategory> roomCategories;
    private List<Service> services;
    private int amountOfAdults;
    private int amountOfChildren;
    private String additionalInformation;
    private LocalDate bookingDate;
    private String bookingNumber;

    // Required by hibernate
    @SuppressWarnings("unused")
    @Generated
    protected Booking() {
    }

    public static Booking create(LocalDate aCheckInDate, LocalDate aCheckOutDate,
                                 BookingId aBookingId, Guest aGuest,
                                 List<Service> aServices, int anAmountOfAdults,
                                 int anAmountOfChildren, String anAdditionalInformation, String aBookingNumber) {

        // If no additional information is provided use 'Nothing' as default
        anAdditionalInformation = anAdditionalInformation.isEmpty() ? "Nothing" : anAdditionalInformation;

        return new Booking(
                aCheckInDate, aCheckOutDate, aBookingId, aGuest,
                aServices, anAmountOfAdults, anAmountOfChildren,
                anAdditionalInformation, LocalDate.now(), aBookingNumber
        );
    }

    private Booking(LocalDate aCheckInDate, LocalDate aCheckOutDate,
                    BookingId aBookingId, Guest aGuest,
                    List<Service> aServices, int anAmountOfAdults,
                    int anAmountOfChildren, String anAdditionalInformation,
                    LocalDate aBookingDate, String aBookingNumber) {

        this.checkInDate = aCheckInDate;
        this.checkOutDate = aCheckOutDate;
        this.isActive = true;
        this.bookingId = aBookingId;
        this.guest = aGuest;
        this.services = aServices;
        this.roomCategories = new ArrayList<>();
        this.amountOfAdults = anAmountOfAdults;
        this.amountOfChildren = anAmountOfChildren;
        this.additionalInformation = anAdditionalInformation;
        this.bookingDate = aBookingDate;
        this.bookingNumber = aBookingNumber;
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

    public int getAmountOfAdults() {
        return amountOfAdults;
    }

    public int getAmountOfChildren() {
        return amountOfChildren;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public void deactivate() {
        isActive = false;
    }

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return isActive == booking.isActive && amountOfAdults == booking.amountOfAdults && amountOfChildren == booking.amountOfChildren && Objects.equals(id, booking.id) && Objects.equals(checkInDate, booking.checkInDate) && Objects.equals(checkOutDate, booking.checkOutDate) && Objects.equals(bookingId, booking.bookingId) && Objects.equals(guest, booking.guest) && Objects.equals(roomCategories, booking.roomCategories) && Objects.equals(services, booking.services) && Objects.equals(additionalInformation, booking.additionalInformation) && Objects.equals(bookingDate, booking.bookingDate) && Objects.equals(bookingNumber, booking.bookingNumber);
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(id, checkInDate, checkOutDate, isActive, bookingId, guest, roomCategories, services, amountOfAdults, amountOfChildren, additionalInformation, bookingDate, bookingNumber);
    }
}
