package at.fhv.se.hotel.domain.model.guest;

import at.fhv.se.hotel.domain.Generated;
import at.fhv.se.hotel.domain.model.booking.Booking;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a hotel guest
 */
public class Guest {
    // Required by hibernate
    @SuppressWarnings("unused")
    private Long id;
    private GuestId guestId;
    private FullName name;
    private Gender gender;
    private Address address;
    private LocalDate birthDate;
    private String phoneNumber;
    private String mailAddress;
    private double discountInPercent;
    private List<Booking> bookings;

    // Required by hibernate
    @SuppressWarnings("unused")
    @Generated
    protected Guest() {
    }

    public static Guest create(GuestId aGuestId, FullName aName, Gender aGender, Address aAddress, LocalDate aBirthdate,
                               String aPhoneNumber, String aMailAddress, double aDiscountInPercent, List<Booking> aBookings) {
        return new Guest(aGuestId, aName, aGender, aAddress, aBirthdate,
                aPhoneNumber, aMailAddress, aDiscountInPercent, aBookings);
    }

    private Guest(GuestId aGuestId, FullName aName, Gender aGender, Address aAddress, LocalDate aBirthdate,
                  String aPhoneNumber, String aMailAddress, double aDiscountInPercent, List<Booking> aBookings) {
        this.guestId = aGuestId;
        this.name = aName;
        this.gender = aGender;
        this.address = aAddress;
        this.birthDate = aBirthdate;
        this.phoneNumber = aPhoneNumber;
        this.mailAddress = aMailAddress;
        this.discountInPercent = aDiscountInPercent;
        this.bookings = aBookings;
    }

    public GuestId getGuestId() {
        return guestId;
    }

    public FullName getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
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

    public double getDiscountInPercent() {
        return discountInPercent;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void modify(FullName aName, Gender aGender, Address aAddress, LocalDate aBirthdate,
                       String aPhoneNumber, String aMailAddress, double aDiscountInPercent
    ) {
        this.name = aName;
        this.gender = aGender;
        this.address = aAddress;
        this.birthDate = aBirthdate;
        this.phoneNumber = aPhoneNumber;
        this.mailAddress = aMailAddress;
        this.discountInPercent = aDiscountInPercent;
    }

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return Objects.equals(id, guest.id) && Objects.equals(guestId, guest.guestId) && Objects.equals(name, guest.name) && gender == guest.gender && Objects.equals(address, guest.address) && Objects.equals(birthDate, guest.birthDate) && Objects.equals(phoneNumber, guest.phoneNumber) && Objects.equals(mailAddress, guest.mailAddress) && Objects.equals(discountInPercent, guest.discountInPercent) && Objects.equals(bookings, guest.bookings);
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(id, guestId, name, gender, address, birthDate, phoneNumber, mailAddress, discountInPercent, bookings);
    }
}

