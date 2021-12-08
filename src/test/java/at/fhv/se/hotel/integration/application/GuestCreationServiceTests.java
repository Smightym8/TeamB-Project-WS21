package at.fhv.se.hotel.integration.application;

import at.fhv.se.hotel.application.api.GuestCreationService;
import at.fhv.se.hotel.application.api.GuestListingService;
import at.fhv.se.hotel.application.dto.GuestDTO;
import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.guest.*;
import at.fhv.se.hotel.domain.repository.GuestRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
public class GuestCreationServiceTests {

    @Autowired
    private GuestCreationService guestCreationService;

    @Autowired
    private GuestRepository guestRepository;

    @Test
    void given_guestdetails_when_creating_guest_then_returnnonemptylist(){
        // given
        String firstNameExpectedStr = "Ali";
        String lastNameExpectedStr = "Cinar";
        String genderExpectedStr = "MALE";
        String mailAddressExpected = "ali.cinar@students.fhv.at";
        String phoneNumberExpected = "+43 676 123 456 789";
        LocalDate birthDateExpected = LocalDate.of(1997, 8,27);
        String streetNameExpectedStr = "Hochschulstraße";
        String streetNumberExpectedStr = "1";
        String zipCodeExpectedStr = "6850";
        String cityExpectedStr = "Dornbirn";
        String countryExpectedStr = "Austria";

        // when
        guestCreationService.createGuest(
                firstNameExpectedStr,
                lastNameExpectedStr,
                genderExpectedStr,
                mailAddressExpected,
                phoneNumberExpected,
                birthDateExpected,
                streetNameExpectedStr,
                streetNumberExpectedStr,
                zipCodeExpectedStr,
                cityExpectedStr,
                countryExpectedStr
        );

        // TODO: nochmal testen wenn findByName implementiert ist
        List<Guest> guestsActual = guestRepository.findAllGuests();

        // then
        assertFalse(guestsActual.isEmpty());
        assertEquals(firstNameExpectedStr, guestsActual.get(0).getName().firstName());
        assertEquals(lastNameExpectedStr, guestsActual.get(0).getName().lastName());
        assertEquals(genderExpectedStr, guestsActual.get(0).getGender().toString());
        assertEquals(mailAddressExpected, guestsActual.get(0).getMailAddress());
        assertEquals(phoneNumberExpected, guestsActual.get(0).getPhoneNumber());
        assertEquals(birthDateExpected, guestsActual.get(0).getBirthDate());
        assertEquals(streetNameExpectedStr, guestsActual.get(0).getAddress().streetName());
        assertEquals(streetNumberExpectedStr, guestsActual.get(0).getAddress().streetNumber());
        assertEquals(zipCodeExpectedStr, guestsActual.get(0).getAddress().zipCode());
        assertEquals(cityExpectedStr, guestsActual.get(0).getAddress().city());
        assertEquals(countryExpectedStr, guestsActual.get(0).getAddress().country());


    }
}
