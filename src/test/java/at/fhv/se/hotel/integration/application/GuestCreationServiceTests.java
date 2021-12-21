package at.fhv.se.hotel.integration.application;

import at.fhv.se.hotel.application.api.GuestCreationService;
import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.guest.*;
import at.fhv.se.hotel.domain.repository.GuestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GuestCreationServiceTests {

    @Autowired
    private GuestCreationService guestCreationService;

    @MockBean
    private GuestRepository guestRepository;

    @Test
    void given_guestDetails_when_create_then_guestIsCreated(){
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
        double discountExpected = 0.0;

        Guest guestExpected = Guest.create(
                new GuestId("1"),
                new FullName("Ali", "Cinar"),
                Gender.MALE,
                new Address("Hochschulstraße",
                        "1", "Dornbirn",
                        "6850", "Austria"),
                LocalDate.of(1997, 8, 27),
                "+43 676 123 456 789",
                "ali.cinar@students.fhv.at",
                0.0,
                Collections.emptyList()
        );

        GuestId guestIdExpected = new GuestId("1");

        Mockito.when(guestRepository.nextIdentity()).thenReturn(guestIdExpected);
        Mockito.when(guestRepository.guestById(guestIdExpected)).thenReturn(Optional.of(guestExpected));
        Mockito.doNothing().when(guestRepository).add(guestExpected);

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
                countryExpectedStr,
                discountExpected
        );

        ArgumentCaptor<Guest> guestCaptor = ArgumentCaptor.forClass(Guest.class);
        Mockito.verify(guestRepository).add(guestCaptor.capture());
        Guest guestActual = guestCaptor.getValue();

        // then
        assertEquals(firstNameExpectedStr, guestActual.getName().firstName());
        assertEquals(lastNameExpectedStr, guestActual.getName().lastName());
        assertEquals(genderExpectedStr, guestActual.getGender().toString());
        assertEquals(mailAddressExpected, guestActual.getMailAddress());
        assertEquals(phoneNumberExpected, guestActual.getPhoneNumber());
        assertEquals(birthDateExpected, guestActual.getBirthDate());
        assertEquals(streetNameExpectedStr, guestActual.getAddress().streetName());
        assertEquals(streetNumberExpectedStr, guestActual.getAddress().streetNumber());
        assertEquals(zipCodeExpectedStr, guestActual.getAddress().zipCode());
        assertEquals(cityExpectedStr, guestActual.getAddress().city());
        assertEquals(countryExpectedStr, guestActual.getAddress().country());
    }
}
