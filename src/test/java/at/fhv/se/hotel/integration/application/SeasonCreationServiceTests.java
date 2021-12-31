package at.fhv.se.hotel.integration.application;

import at.fhv.se.hotel.application.api.SeasonCreationService;
import at.fhv.se.hotel.domain.model.season.Season;
import at.fhv.se.hotel.domain.model.season.SeasonId;
import at.fhv.se.hotel.domain.model.season.SeasonName;
import at.fhv.se.hotel.domain.repository.SeasonRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SeasonCreationServiceTests {
    @Autowired
    SeasonCreationService seasonCreationService;

    @MockBean
    SeasonRepository seasonRepository;

    @Test
    public void given_season_details_when_create_season_then_season_with_matching_details_created() {
        // given
        String seasonNameExpectedStr = "Summer Season";
        LocalDate startDateExpected = LocalDate.of(2021,7,1);
        LocalDate endDateExpected = LocalDate.of(2021, 9, 30);

        SeasonId seasonIdExpected = new SeasonId("1");
        SeasonName seasonNameExpected = new SeasonName(seasonNameExpectedStr);
        Season seasonExpected = Season.create(
                seasonIdExpected,
                seasonNameExpected,
                startDateExpected,
                endDateExpected
        );

        Mockito.when(seasonRepository.nextIdentity()).thenReturn(seasonIdExpected);
        Mockito.doNothing().when(seasonRepository).add(seasonExpected);

        // when
        seasonCreationService.createSeason(
                seasonNameExpectedStr,
                startDateExpected,
                endDateExpected
        );

        ArgumentCaptor<Season> seasonCaptor = ArgumentCaptor.forClass(Season.class);
        Mockito.verify(seasonRepository).add(seasonCaptor.capture());
        Season seasonActual = seasonCaptor.getValue();

        // then
        assertEquals(seasonExpected.getSeasonId().id(), seasonActual.getSeasonId().id());
        assertEquals(seasonExpected.getSeasonName().name(), seasonActual.getSeasonName().name());
        assertEquals(seasonExpected.getStartDate(), seasonActual.getStartDate());
        assertEquals(seasonExpected.getEndDate(), seasonActual.getEndDate());
    }
}
