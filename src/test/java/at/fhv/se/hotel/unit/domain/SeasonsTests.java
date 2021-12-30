package at.fhv.se.hotel.unit.domain;

import at.fhv.se.hotel.domain.model.season.SeasonId;
import at.fhv.se.hotel.domain.model.season.SeasonName;
import at.fhv.se.hotel.domain.model.roomcategory.Season;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// TODO: Replace Enum Season with class Season
public class SeasonsTests {

    @Test
    public void given_seasonDetails_when_createSeason_then_detailsequals() {
        // given
        SeasonId seasonIdExpected = new SeasonId("1");
        SeasonName seasonNameExpected = new SeasonName("Winter");
        LocalDate startDateExpected = LocalDate.of(2021, 12, 1);
        LocalDate endDateExpected = LocalDate.of(2022, 1, 31);

        // when
        // TODO: at.fhv.se.hotel.domain.model.Season.Season -> Season after replacing enum with class
        at.fhv.se.hotel.domain.model.season.Season seasonActual = at.fhv.se.hotel.domain.model.season.Season.create(
                seasonIdExpected,
                seasonNameExpected,
                startDateExpected,
                endDateExpected
        );

        // then
        assertEquals(seasonIdExpected, seasonActual.getSeasonId());
        assertEquals(seasonIdExpected.id(), seasonActual.getSeasonId().id());
        assertEquals(seasonNameExpected, seasonActual.getSeasonName());
        assertEquals(seasonNameExpected.name(), seasonActual.getSeasonName().name());
        assertEquals(startDateExpected, seasonActual.getStartDate());
        assertEquals(endDateExpected, seasonActual.getEndDate());
    }

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

    @Test
    void given_checkincheckoutdateoverlappingseasonborder_when_booking_then_returnmatchingseasons() {
        // given
        LocalDate checkInDate = LocalDate.of(2022, 11, 30);
        LocalDate checkOutDate = LocalDate.of(2022, 12, 1);
        List<Season> seasonsExpected = List.of(Season.WINTER, Season.SUMMER);

        // when
        List<Season> seasonsActual = Season.seasons(checkInDate, checkOutDate);

        // then
        assertEquals(seasonsExpected.size(), seasonsActual.size());
        for (Season s : seasonsActual) {
            assertTrue(seasonsExpected.contains(s));
        }
    }

    @Test
    void given_wholeyear_when_booking_then_returnallseasons() {
        // given
        LocalDate checkInDate = LocalDate.of(2022, 1, 1);
        LocalDate checkOutDate = LocalDate.of(2022, 12, 31);
        List<Season> seasonsExpected = List.of(Season.WINTER, Season.SUMMER, Season.SPRING);

        // when
        List<Season> seasonsActual = Season.seasons(checkInDate, checkOutDate);

        // then
        assertEquals(seasonsExpected.size(), seasonsActual.size());
        for (Season s : seasonsActual) {
            assertTrue(seasonsExpected.contains(s));
        }
    }

    @Test
    void given_currentstaydateandseason_then_returntrue() {
        // given
        LocalDate currentDate = LocalDate.of(2021,11,26);
        Season expectedSeason = Season.SUMMER;

        // when ... then
        assertTrue(Season.isInSeason(currentDate, expectedSeason), "Season does not match");
    }

    @Test
    void given_3datesand3seasons_then_returnalltrue() {
        // given
        LocalDate date1 = LocalDate.of(2021,11,26);
        Season expectedSeason1 = Season.SUMMER;

        LocalDate date2 = LocalDate.of(2021,12,12);
        Season expectedSeason2 = Season.WINTER;

        LocalDate date3 = LocalDate.of(2022,2,5);
        Season expectedSeason3 = Season.SPRING;

        // when ... then
        assertTrue(Season.isInSeason(date1, expectedSeason1), "Season does not match");
        assertTrue(Season.isInSeason(date2, expectedSeason2), "Season does not match");
        assertTrue(Season.isInSeason(date3, expectedSeason3), "Season does not match");
    }

    @Test
    void given_6currentstaydatesonseasonborderand3seasons_then_returntrue() {
        // given
        LocalDate startDate1 = LocalDate.of(2021,6,1);
        LocalDate endDate1 = LocalDate.of(2021,11,30);
        Season expectedSeason1 = Season.SUMMER;

        LocalDate startDate2 = LocalDate.of(2021,12,1);
        LocalDate endDate2 = LocalDate.of(2022,1,31);
        Season expectedSeason2 = Season.WINTER;

        LocalDate startDate3 = LocalDate.of(2021,2,1);
        LocalDate endDate3 = LocalDate.of(2021,5,31);
        Season expectedSeason3 = Season.SPRING;

        // when ... then
        assertTrue(Season.isInSeason(startDate1, expectedSeason1), "Summer Start does not match");
        assertTrue(Season.isInSeason(endDate1, expectedSeason1), "Summer end does not match");
        assertTrue(Season.isInSeason(startDate2, expectedSeason2), "Winter start does not match");
        assertTrue(Season.isInSeason(endDate2, expectedSeason2), "Winter end does not match");
        assertTrue(Season.isInSeason(startDate3, expectedSeason3), "Spring start does not match");
        assertTrue(Season.isInSeason(endDate3, expectedSeason3), "Spring end does not match");
    }
}
