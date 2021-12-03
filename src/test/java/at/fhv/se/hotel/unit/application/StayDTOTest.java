package at.fhv.se.hotel.unit.application;

import at.fhv.se.hotel.application.dto.StayDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StayDTOTest {

    @Test
    void given_staytodetails_when_createstayto_then_detailsequals() {
        //given
        String idExpected = "10";
        String guestFirstNameExpected = "John";
        String guestLastNameExpected = "Doe";
        LocalDate checkOutDateExpected = LocalDate.of(2021, 12, 1);

        //when
        StayDTO stayDTOExpected = StayDTO.builder()
                .withId(idExpected)
                .withGuestFirstName(guestFirstNameExpected)
                .withGuestLastName(guestLastNameExpected)
                .withCheckOutDate(checkOutDateExpected)
                .build();

        //then
        assertEquals(idExpected,stayDTOExpected.id());
        assertEquals(guestFirstNameExpected, stayDTOExpected.guestFirstName());
        assertEquals(guestLastNameExpected, stayDTOExpected.guestLastName());
        assertEquals(checkOutDateExpected, stayDTOExpected.checkOutDate());
    }
}
