package at.fhv.se.hotel.domain.model.guest;

import at.fhv.se.hotel.domain.model.booking.Booking;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Guest {
    // Required by hibernate
    private Long id;
    private GuestId guestId;
    private FullName name;
    private Address address;
    private LocalDate birthDate;
    private String phoneNumber;
    private String mailAddress;
    private List<Booking> bookings;

    // Required by hibernate
    private Guest() {}

    public static Guest create (GuestId aGuestId, FullName aName, Address aAddress, LocalDate aBirthdate,
                                String aPhoneNumber, String aMailAddress, List<Booking> aBookings) {
        return new Guest(aGuestId, aName, aAddress, aBirthdate, aPhoneNumber, aMailAddress, aBookings);
    }

    private Guest(GuestId aGuestId, FullName aName, Address aAddress, LocalDate aBirthdate,
                  String aPhoneNumber, String aMailAddress, List<Booking> aBookings) {
        this.guestId = aGuestId;
        this.name = aName;
        this.address = aAddress;
        this.birthDate = aBirthdate;
        this.phoneNumber = aPhoneNumber;
        this.mailAddress = aMailAddress;
        this.bookings = aBookings;
    }

    public GuestId getGuestId() {
        return guestId;
    }

    public void setGuestId(GuestId guestId) {
        this.guestId = guestId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return Objects.equals(id, guest.id)
                && Objects.equals(guestId, guest.guestId)
                && Objects.equals(name, guest.name)
                && Objects.equals(address, guest.address)
                && Objects.equals(birthDate, guest.birthDate)
                && Objects.equals(phoneNumber, guest.phoneNumber)
                && Objects.equals(mailAddress, guest.mailAddress)
                && Objects.equals(bookings, guest.bookings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, guestId, name,
                address, birthDate, phoneNumber,
                mailAddress, bookings);
    }
}
