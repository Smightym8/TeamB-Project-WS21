package at.fhv.se.hotel.integration.infrastructure;

import at.fhv.se.hotel.domain.model.roomcategory.*;
import at.fhv.se.hotel.domain.model.season.Season;
import at.fhv.se.hotel.domain.model.season.SeasonId;
import at.fhv.se.hotel.domain.model.season.SeasonName;
import at.fhv.se.hotel.domain.repository.RoomCategoryPriceRepository;
import at.fhv.se.hotel.domain.repository.SeasonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class RoomCategoryPriceRepositoryImplTests {

    @Autowired
    SeasonRepository seasonRepository;

    @Autowired
    private RoomCategoryPriceRepository roomCategoryPriceRepository;

    @Autowired
    private EntityManager em;

    @Test
    void given_categoryandseason_when_fetchingprice_then_returnequalprices() {
        // given
        RoomCategory singleRoom = RoomCategory.create(
                new RoomCategoryId(UUID.randomUUID().toString().toUpperCase()),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        Season winterSeason = Season.create(
                new SeasonId("1"),
                new SeasonName("Winter "),
                LocalDate.of(2021, 12, 1),
                LocalDate.of(2022, 1, 31)
        );

        RoomCategoryPrice priceExpected = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                winterSeason,
                singleRoom,
                new BigDecimal("300")
        );

        // when
        seasonRepository.add(winterSeason);
        roomCategoryPriceRepository.add(priceExpected);
        em.flush();

        RoomCategoryPrice priceActual = roomCategoryPriceRepository.priceBySeasonAndCategory(
                winterSeason.getSeasonId(), singleRoom.getRoomCategoryId()
        ).get();

        // then
        assertEquals(priceExpected.getPrice(), priceActual.getPrice());

    }
}
