package at.fhv.se.hotel.domain.models.Guest;

import at.fhv.se.hotel.domain.models.Booking.Booking;

import java.time.LocalDate;
import java.util.List;

public class Guest {
    private FullName name;
    private Address address;
    private LocalDate birthDate;
    private String phoneNumber;
    private String mailAddress;
    private List<Booking> bookings;

    public Guest(FullName aName, Address aAddress, LocalDate aBirthdate, String aPhoneNumber, String aMailAddress, List<Booking> aBookings) {
        this.name = aName;
        this.address = aAddress;
        this.birthDate = aBirthdate;
        this.phoneNumber = aPhoneNumber;
        this.mailAddress = aMailAddress;
        this.bookings = aBookings;
    }

    public FullName getName() {
        return name;
    }

    public void setName(FullName name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
