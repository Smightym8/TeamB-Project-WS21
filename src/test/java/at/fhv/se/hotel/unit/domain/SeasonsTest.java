package at.fhv.se.hotel.unit.domain;

import at.fhv.se.hotel.domain.model.roomcategory.Season;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SeasonsTest {

    @Test
    void given_checkincheckoutdate_when_booking_then_returnmatchingseason() {
        // given
        LocalDate checkInDate = LocalDate.of(2022, 12, 1);
        LocalDate checkOutDate = LocalDate.of(2022, 12, 10);
        List<Season> seasonsExpected = List.of(Season.WINTER);

        // when
        List<Season> seasonsActual = Season.seasons(checkInDate, checkOutDate);

        // then
        assertEquals(seasonsExpected.size(), seasonsActual.size());
        for (Season s : seasonsActual) {
            assertTrue(seasonsExpected.contains(s));
        }
    }

    @Test
    void given_checkincheckoutdate_when_booking_then_returnmatchingseasons() {
        // given
        LocalDate checkInDate = LocalDate.of(2022, 11, 25);
        LocalDate checkOutDate = LocalDate.of(2022, 12, 5);
        List<Season> seasonsExpected = List.of(Season.WINTER, Season.SUMMER);

        // when
        List<Season> seasonsActual = Season.seasons(checkInDate, checkOutDate);

        // then
        assertEquals(seasonsExpected.size(), seasonsActual.size());
        for (Season s : seasonsActual) {
            assertTrue(seasonsExpected.contains(s));
        }
    }
}
