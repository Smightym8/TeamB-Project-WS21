package at.fhv.se.hotel.unit.domain;

import at.fhv.se.hotel.domain.model.season.Season;
import at.fhv.se.hotel.domain.model.season.SeasonId;
import at.fhv.se.hotel.domain.model.season.SeasonName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class SeasonTests {

    @Test
    public void given_seasonDetails_when_createSeason_then_detailsequals() {
        // given
        SeasonId seasonIdExpected = new SeasonId("1");
        SeasonName seasonNameExpected = new SeasonName("Winter");
        LocalDate startDateExpected = LocalDate.of(2021, 12, 1);
        LocalDate endDateExpected = LocalDate.of(2022, 1, 31);

        // when
        Season seasonActual = Season.create(
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
}
