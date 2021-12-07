package at.fhv.se.hotel.unit.domain;

import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.guest.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GuestTests {

    @Test
    void given_guestdetails_when_creating_guest_then_details_equals() {
        // given
        String guestIdStr = "1";
        GuestId guestId = new GuestId(guestIdStr);

        String firstNameStr = "Ali";
        String lastNameStr = "Cinar";
        FullName guestName = new FullName(firstNameStr, lastNameStr);

        Gender gender = Gender.MALE;

        String streetNameStr = "Hochschulstraße";
        String streetNumberStr = "1";
        String cityStr = "Dornbirn";
        String zipCodeStr = "6850";
        String countryStr = "Austria";
        Address address = new Address(
                streetNameStr,
                streetNumberStr,
                cityStr,
                zipCodeStr,
                countryStr
        );

        LocalDate birthDate = LocalDate.of(1997, 8,27);
        String phoneNumber = "+43 676 123 456 789";
        String mailAddress = "ali.cinar@students.fhv.at";
        List<Booking> bookings = Collections.emptyList();

        // when
        Guest guest = Guest.create(
                guestId,
                guestName,
                gender,
                address,
                birthDate,
                phoneNumber,
                mailAddress,
                bookings
        );

        //then
        assertEquals(guestId, guest.getGuestId());
        assertEquals(guestIdStr, guest.getGuestId().id());
        assertEquals(guestName, guest.getName());
        assertEquals(firstNameStr, guest.getName().firstName());
        assertEquals(lastNameStr, guest.getName().lastName());
        assertEquals(gender, guest.getGender());
        assertEquals(address, guest.getAddress());
        assertEquals(streetNameStr, guest.getAddress().streetName());
        assertEquals(streetNumberStr, guest.getAddress().streetNumber());
        assertEquals(cityStr, guest.getAddress().city());
        assertEquals(zipCodeStr, guest.getAddress().zipCode());
        assertEquals(countryStr, guest.getAddress().country());
        assertEquals(birthDate, guest.getBirthDate());
        assertEquals(phoneNumber, guest.getPhoneNumber());
        assertEquals(mailAddress, guest.getMailAddress());
        assertEquals(bookings.size(), guest.getBookings().size());

    }

}
