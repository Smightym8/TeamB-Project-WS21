package at.fhv.se.hotel.unit.application;

import at.fhv.se.hotel.application.dto.StayListingDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StayListingDTOTest {

    @Test
    void given_staytodetails_when_createstayto_then_detailsequals() {
        //given
        String idExpected = "10";
        String guestFirstNameExpected = "John";
        String guestLastNameExpected = "Doe";
        LocalDate checkOutDateExpected = LocalDate.of(2021, 12, 1);
        List<String> roomsExpected = List.of("S101", "S102");

        //when
        StayListingDTO stayListingDTOExpected = StayListingDTO.builder()
                .withId(idExpected)
                .withGuestFirstName(guestFirstNameExpected)
                .withGuestLastName(guestLastNameExpected)
                .withCheckOutDate(checkOutDateExpected)
                .withRooms(roomsExpected)
                .build();

        //then
        assertEquals(idExpected,stayListingDTOExpected.id());
        assertEquals(guestFirstNameExpected, stayListingDTOExpected.guestFirstName());
        assertEquals(guestLastNameExpected, stayListingDTOExpected.guestLastName());
        assertEquals(checkOutDateExpected, stayListingDTOExpected.checkOutDate());
        assertEquals(roomsExpected.size(), stayListingDTOExpected.rooms().size());
    }
}
