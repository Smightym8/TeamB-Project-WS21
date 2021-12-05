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
        String streetNameExpected = "Streetname";
        String streetNumberExpected = "1a";
        String cityExpected = "City";
        String zipCodeExpected = "6850";
        String countryExpected = "Austria";

        // when
        GuestDTO guestDTOExpected = GuestDTO.builder()
                .withId(idExpected)
                .withFirstName(firstNameExpected)
                .withLastName(lastNameExpected)
                .withStreetName(streetNameExpected)
                .withStreetNumber(streetNumberExpected)
                .withCity(cityExpected)
                .withZipCode(zipCodeExpected)
                .withCountry(countryExpected)
                .build();

        // then
        assertEquals(idExpected, guestDTOExpected.id());
        assertEquals(firstNameExpected, guestDTOExpected.firstName());
        assertEquals(lastNameExpected, guestDTOExpected.lastName());
    }
}
