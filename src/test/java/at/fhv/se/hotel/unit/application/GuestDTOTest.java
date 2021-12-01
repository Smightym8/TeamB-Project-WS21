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
        //LocalDate birthDateExpected = LocalDate.of(1980, 1, 1);
        String streetName = "Samplestreet";
        String streetNumber = "1";
        String city = "Dornbirn";
        String zipCode = "6850";
        String country = "Vorarlberg";

        // when
        GuestDTO guestDTOExpected = GuestDTO.builder()
                .withId(idExpected)
                .withFirstName(firstNameExpected)
                .withLastName(lastNameExpected)
                //.withBirthDate(birthDateExpected)
                .build();

        // then
        assertEquals(idExpected, guestDTOExpected.id());
        assertEquals(firstNameExpected, guestDTOExpected.firstName());
        assertEquals(lastNameExpected, guestDTOExpected.lastName());
        //assertEquals(birthDateExpected, guestDTOExpected.birthDate());
    }
}
