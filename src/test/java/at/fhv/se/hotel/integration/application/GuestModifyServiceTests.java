package at.fhv.se.hotel.integration.application;

import at.fhv.se.hotel.application.api.GuestModifyService;
import at.fhv.se.hotel.application.api.exception.GuestNotFoundException;
import at.fhv.se.hotel.domain.model.guest.*;
import at.fhv.se.hotel.domain.repository.GuestRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class GuestModifyServiceTests {
    @Autowired
    GuestModifyService guestModifyService;

    @MockBean
    GuestRepository guestRepository;

    @Test
    public void given_guest_when_modifyGuest_then_guestShouldBeUpdated() throws GuestNotFoundException {
        // given
        // Initial values for guest
        String guestIdStrExpected = "42";
        GuestId guestIdExpected = new GuestId(guestIdStrExpected);
        String firstNameExpected = "John";
        String lastNameExpected = "Doe";
        String genderExpected = "Male";
        String streetNameExpected= "Street";
        String streetNumberExpected = "1";
        String cityExpected = "Dornbirn";
        String zipCodeExpected = "6850";
        String countryExpected = "Austria";
        LocalDate dateOfBirthExpected = LocalDate.of(1800, 1, 1);
        String phoneNumberExpected = "+43 12 12 12 12 12 12";
        String mailAddressExpected = "john.doe@tdd.at";
        double discountExpected = 0;

        // Modified address
        String streetNameExpectedNew = "Modified Streetname";
        String streetNumberExpectedNew = "42";
        String cityExpectedNew = "Bregenz";
        String zipCodeExpectedNew = "6900";

        Guest guestExpected = Guest.create(
                guestIdExpected,
                new FullName(firstNameExpected, lastNameExpected),
                Gender.MALE,
                new Address(streetNameExpected, streetNumberExpected, cityExpected, zipCodeExpected, countryExpected),
                dateOfBirthExpected,
                phoneNumberExpected,
                mailAddressExpected,
                discountExpected,
                Collections.emptyList()
        );

        Mockito.when(guestRepository.guestById(guestIdExpected)).thenReturn(Optional.of(guestExpected));

        // when
        guestModifyService.modifyGuest(
                guestIdStrExpected,
                firstNameExpected,
                lastNameExpected,
                genderExpected,
                streetNameExpectedNew,
                streetNumberExpectedNew,
                cityExpectedNew,
                zipCodeExpectedNew,
                countryExpected,
                dateOfBirthExpected,
                phoneNumberExpected,
                mailAddressExpected,
                discountExpected
        );

        // then
        assertEquals(guestExpected.getGuestId(), guestIdExpected);
        assertEquals(guestExpected.getGuestId().id(), guestIdStrExpected);
        assertEquals(guestExpected.getName().firstName(), firstNameExpected);
        assertEquals(guestExpected.getName().lastName(), lastNameExpected);
        assertEquals(guestExpected.getGender().name(), genderExpected.toUpperCase());
        assertEquals(guestExpected.getAddress().streetName(), streetNameExpectedNew);
        assertEquals(guestExpected.getAddress().streetNumber(), streetNumberExpectedNew);
        assertEquals(guestExpected.getAddress().city(), cityExpectedNew);
        assertEquals(guestExpected.getAddress().zipCode(), zipCodeExpectedNew);
        assertEquals(guestExpected.getAddress().country(), countryExpected);
        assertEquals(guestExpected.getBirthDate(), dateOfBirthExpected);
        assertEquals(guestExpected.getPhoneNumber(), phoneNumberExpected);
        assertEquals(guestExpected.getMailAddress(), mailAddressExpected);
        assertEquals(guestExpected.getDiscountInPercent(), discountExpected);

        // Check if old values don't match anymore
        assertNotEquals(guestExpected.getAddress().streetName(), streetNameExpected);
        assertNotEquals(guestExpected.getAddress().streetNumber(), streetNumberExpected);
        assertNotEquals(guestExpected.getAddress().city(), cityExpected);
        assertNotEquals(guestExpected.getAddress().zipCode(), zipCodeExpected);
    }

    @Test
    public void given_missingGuest_when_modifyGuest_then_GuestNotFoundExceptionIsThrown() {
        // given
        // Initial values for guest
        String guestIdStrExpected = "42";
        GuestId guestIdExpected = new GuestId(guestIdStrExpected);
        String firstNameExpected = "John";
        String lastNameExpected = "Doe";
        String genderExpected = "Male";
        String countryExpected = "Austria";
        LocalDate dateOfBirthExpected = LocalDate.of(1800, 1, 1);
        String phoneNumberExpected = "+43 12 12 12 12 12 12";
        String mailAddressExpected = "john.doe@tdd.at";
        double discountExpected = 0;

        // Modified address
        String streetNameExpectedNew = "Modified Streetname";
        String streetNumberExpectedNew = "42";
        String cityExpectedNew = "Bregenz";
        String zipCodeExpectedNew = "6900";


        Mockito.when(guestRepository.guestById(guestIdExpected)).thenReturn(Optional.empty());

        // when
        // when ... then
        Exception exception = assertThrows(GuestNotFoundException.class, () -> {
            guestModifyService.modifyGuest(
                    guestIdStrExpected,
                    firstNameExpected,
                    lastNameExpected,
                    genderExpected,
                    streetNameExpectedNew,
                    streetNumberExpectedNew,
                    cityExpectedNew,
                    zipCodeExpectedNew,
                    countryExpected,
                    dateOfBirthExpected,
                    phoneNumberExpected,
                    mailAddressExpected,
                    discountExpected
            );
        });

        String expectedMessage = "Guest with id " + guestIdStrExpected + " not found";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
