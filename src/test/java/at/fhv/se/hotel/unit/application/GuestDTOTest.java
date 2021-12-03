package at.fhv.se.hotel.unit.application;

import at.fhv.se.hotel.application.dto.GuestDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GuestDTOTest {
    @Test
    void given_guestdtodetails_when_createguestdto_then_detailsequals() {
        // given
        String idExpected = "42";
        String firstNameExpected = "John";
        String lastNameExpected = "Doe";
<<<<<<< HEAD
        //LocalDate birthDateExpected = LocalDate.of(1980, 1, 1);
        String streetName = "Samplestreet";
        String streetNumber = "1";
        String city = "Dornbirn";
        String zipCode = "6850";
        String country = "Vorarlberg";
=======
        String streetNameExpected = "Streetname";
        String streetNumberExpected = "1a";
        String cityExpected = "City";
        String zipCodeExpected = "6850";
        String countryExpected = "Austria";
>>>>>>> development

        // when
        GuestDTO guestDTOExpected = GuestDTO.builder()
                .withId(idExpected)
                .withFirstName(firstNameExpected)
                .withLastName(lastNameExpected)
<<<<<<< HEAD
                //.withBirthDate(birthDateExpected)
=======
                .withStreetName(streetNameExpected)
                .withStreetNumber(streetNumberExpected)
                .withCity(cityExpected)
                .withZipCode(zipCodeExpected)
                .withCountry(countryExpected)
>>>>>>> development
                .build();

        // then
        assertEquals(idExpected, guestDTOExpected.id());
        assertEquals(firstNameExpected, guestDTOExpected.firstName());
        assertEquals(lastNameExpected, guestDTOExpected.lastName());
<<<<<<< HEAD
        //assertEquals(birthDateExpected, guestDTOExpected.birthDate());
=======
>>>>>>> development
    }
}
