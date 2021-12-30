package at.fhv.se.hotel.integration.infrastructure;

import at.fhv.se.hotel.domain.model.season.Season;
import at.fhv.se.hotel.domain.model.season.SeasonId;
import at.fhv.se.hotel.domain.model.season.SeasonName;
import at.fhv.se.hotel.domain.repository.SeasonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class SeasonRepositoryImplTests {
    @Autowired
    SeasonRepository seasonRepository;

    @Autowired
    private EntityManager em;

    @Test
    void given_3Seasons_when_addSeasonsToRepository_then_return3EqualsSeasons() {
        // given
        List<Season> seasonsExpected = List.of(
                Season.create(
                        new SeasonId("1"),
                        new SeasonName("Winter"),
                        LocalDate.of(2021, 12, 1),
                        LocalDate.of(2022, 1, 31)
                ),
                Season.create(
                        new SeasonId("2"),
                        new SeasonName("Spring"),
                        LocalDate.of(2021, 2, 1),
                        LocalDate.of(2022, 5, 31)
                ),
                Season.create(
                        new SeasonId("3"),
                        new SeasonName("Fall"),
                        LocalDate.of(2021, 6, 1),
                        LocalDate.of(2022, 11, 30)
                )
        );

        seasonsExpected.forEach(season -> seasonRepository.add(season));
        this.em.flush();

        // when
        List<Season> seasonsActual = seasonRepository.findAllSeasons();

        // then
        assertEquals(seasonsExpected.size(), seasonsActual.size());

        for(Season s : seasonsActual) {
            assertTrue(seasonsExpected.contains(s));
        }
    }
}
