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
    public Guest() {}

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

    public FullName getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
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
