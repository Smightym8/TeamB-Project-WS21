package at.fhv.se.hotel.integration.application;

import at.fhv.se.hotel.application.api.SeasonListingService;
import at.fhv.se.hotel.application.dto.SeasonWithPricesDTO;
import at.fhv.se.hotel.domain.model.roomcategory.*;
import at.fhv.se.hotel.domain.model.season.Season;
import at.fhv.se.hotel.domain.model.season.SeasonId;
import at.fhv.se.hotel.domain.model.season.SeasonName;
import at.fhv.se.hotel.domain.repository.RoomCategoryPriceRepository;
import at.fhv.se.hotel.domain.repository.SeasonRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class SeasonListingServiceTests {
    @Autowired
    SeasonListingService seasonListingService;

    @MockBean
    SeasonRepository seasonRepository;

    @MockBean
    RoomCategoryPriceRepository roomCategoryPriceRepository;

    @Test
    void given_3seasons_with_roomcategoryprices_in_repository_when_fetching_all_then_return_equal_seasons_with_prices() {
        // given
        RoomCategory singleRoomExpected = RoomCategory.create(
                new RoomCategoryId("1"),
                new RoomCategoryName("Single Room"),
                new Description("This is a Single Room")
        );

        RoomCategory doubleRoomExpected = RoomCategory.create(
                new RoomCategoryId("2"),
                new RoomCategoryName("Double Room"),
                new Description("This is a Double Room")
        );

        RoomCategory juniorSuiteExpected = RoomCategory.create(
                new RoomCategoryId("3"),
                new RoomCategoryName("Junior Suite"),
                new Description("This is a Junior Suite")
        );

        RoomCategory suiteExpected = RoomCategory.create(
                new RoomCategoryId("4"),
                new RoomCategoryName("Suite"),
                new Description("This is a Suite")
        );

        /* -- Spring Season -- */
        SeasonId springSeasonIdExpected = new SeasonId("1");
        SeasonName springSeasonNameExpected = new SeasonName("Spring");
        Season springSeasonExpected = Season.create(
                springSeasonIdExpected,
                springSeasonNameExpected,
                LocalDate.of(2021, 2,1),
                LocalDate.of(2021, 5, 31)
        );

        BigDecimal springSeasonSingleRoomPriceExpectedBigDecimal = new BigDecimal("20");
        RoomCategoryPriceId springSeasonSingleRoomPriceIdExpected = new RoomCategoryPriceId("1");
        RoomCategoryPrice springSeasonSingleRoomPriceExpected = RoomCategoryPrice.create(
                springSeasonSingleRoomPriceIdExpected,
                springSeasonExpected,
                singleRoomExpected,
                springSeasonSingleRoomPriceExpectedBigDecimal
        );

        BigDecimal springSeasonDoubleRoomPriceExpectedBigDecimal = new BigDecimal("40");
        RoomCategoryPriceId springSeasonDoubleRoomPriceIdExpected = new RoomCategoryPriceId("2");
        RoomCategoryPrice springSeasonDoubleRoomPriceExpected = RoomCategoryPrice.create(
                springSeasonDoubleRoomPriceIdExpected,
                springSeasonExpected,
                doubleRoomExpected,
                springSeasonDoubleRoomPriceExpectedBigDecimal
        );

        BigDecimal springSeasonJuniorSuitePriceExpectedBigDecimal = new BigDecimal("60");
        RoomCategoryPriceId springSeasonJuniorSuitePriceIdExpected = new RoomCategoryPriceId("3");
        RoomCategoryPrice springSeasonJuniorSuitePriceExpected = RoomCategoryPrice.create(
                springSeasonJuniorSuitePriceIdExpected,
                springSeasonExpected,
                juniorSuiteExpected,
                springSeasonJuniorSuitePriceExpectedBigDecimal
        );

        BigDecimal springSeasonSuitePriceExpectedBigDecimal = new BigDecimal("80");
        RoomCategoryPriceId springSeasonSuitePriceIdExpected = new RoomCategoryPriceId("4");
        RoomCategoryPrice springSeasonSuitePriceExpected = RoomCategoryPrice.create(
                springSeasonSuitePriceIdExpected,
                springSeasonExpected,
                suiteExpected,
                springSeasonSuitePriceExpectedBigDecimal
        );

        /* -- Summer Season -- */
        SeasonId summerSeasonIdExpected = new SeasonId("2");
        SeasonName summerSeasonNameExpected = new SeasonName("Summer");
        Season summerSeasonExpected = Season.create(
                summerSeasonIdExpected,
                summerSeasonNameExpected,
                LocalDate.of(2021, 7,1),
                LocalDate.of(2021, 9, 30)
        );

        BigDecimal summerSeasonSingleRoomPriceExpectedBigDecimal = new BigDecimal("40");
        RoomCategoryPriceId summerSeasonSingleRoomPriceIdExpected = new RoomCategoryPriceId("5");
        RoomCategoryPrice summerSeasonSingleRoomPriceExpected = RoomCategoryPrice.create(
                summerSeasonSingleRoomPriceIdExpected,
                summerSeasonExpected,
                singleRoomExpected,
                summerSeasonSingleRoomPriceExpectedBigDecimal
        );

        BigDecimal summerSeasonDoubleRoomPriceExpectedBigDecimal = new BigDecimal("80");
        RoomCategoryPriceId summerSeasonDoubleRoomPriceIdExpected = new RoomCategoryPriceId("6");
        RoomCategoryPrice summerSeasonDoubleRoomPriceExpected = RoomCategoryPrice.create(
                summerSeasonDoubleRoomPriceIdExpected,
                summerSeasonExpected,
                doubleRoomExpected,
                summerSeasonDoubleRoomPriceExpectedBigDecimal
        );

        BigDecimal summerSeasonJuniorSuitePriceExpectedBigDecimal = new BigDecimal("120");
        RoomCategoryPriceId summerSeasonJuniorSuitePriceIdExpected = new RoomCategoryPriceId("7");
        RoomCategoryPrice summerSeasonJuniorSuitePriceExpected = RoomCategoryPrice.create(
                summerSeasonJuniorSuitePriceIdExpected,
                summerSeasonExpected,
                juniorSuiteExpected,
                summerSeasonJuniorSuitePriceExpectedBigDecimal
        );

        BigDecimal summerSeasonSuitePriceExpectedBigDecimal = new BigDecimal("160");
        RoomCategoryPriceId summerSeasonSuitePriceIdExpected = new RoomCategoryPriceId("8");
        RoomCategoryPrice summerSeasonSuitePriceExpected = RoomCategoryPrice.create(
                summerSeasonSuitePriceIdExpected,
                summerSeasonExpected,
                suiteExpected,
                summerSeasonSuitePriceExpectedBigDecimal
        );

        /* -- Fall Season -- */
        SeasonId fallSeasonIdExpected = new SeasonId("3");
        SeasonName fallSeasonNameExpected = new SeasonName("Fall");
        Season fallSeasonExpected = Season.create(
                fallSeasonIdExpected,
                fallSeasonNameExpected,
                LocalDate.of(2021, 10,1),
                LocalDate.of(2021, 11, 30)
        );

        BigDecimal fallSeasonSingleRoomPriceExpectedBigDecimal = new BigDecimal("25");
        RoomCategoryPriceId fallSeasonSingleRoomPriceIdExpected = new RoomCategoryPriceId("9");
        RoomCategoryPrice fallSeasonSingleRoomPriceExpected = RoomCategoryPrice.create(
                fallSeasonSingleRoomPriceIdExpected,
                fallSeasonExpected,
                singleRoomExpected,
                fallSeasonSingleRoomPriceExpectedBigDecimal
        );

        BigDecimal fallSeasonDoubleRoomPriceExpectedBigDecimal = new BigDecimal("50");
        RoomCategoryPriceId fallSeasonDoubleRoomPriceIdExpected = new RoomCategoryPriceId("10");
        RoomCategoryPrice fallSeasonDoubleRoomPriceExpected = RoomCategoryPrice.create(
                fallSeasonDoubleRoomPriceIdExpected,
                fallSeasonExpected,
                doubleRoomExpected,
                fallSeasonDoubleRoomPriceExpectedBigDecimal
        );

        BigDecimal fallSeasonJuniorSuitePriceExpectedBigDecimal = new BigDecimal("75");
        RoomCategoryPriceId fallSeasonJuniorSuitePriceIdExpected = new RoomCategoryPriceId("11");
        RoomCategoryPrice fallSeasonJuniorSuitePriceExpected = RoomCategoryPrice.create(
                fallSeasonJuniorSuitePriceIdExpected,
                fallSeasonExpected,
                juniorSuiteExpected,
                fallSeasonJuniorSuitePriceExpectedBigDecimal
        );

        BigDecimal fallSeasonSuitePriceExpectedBigDecimal = new BigDecimal("100");
        RoomCategoryPriceId fallSeasonSuitePriceIdExpected = new RoomCategoryPriceId("12");
        RoomCategoryPrice fallSeasonSuitePriceExpected = RoomCategoryPrice.create(
                fallSeasonSuitePriceIdExpected,
                fallSeasonExpected,
                suiteExpected,
                fallSeasonSuitePriceExpectedBigDecimal
        );

        List<RoomCategoryPrice> seasonsWithPricesExpected = List.of(
                springSeasonSingleRoomPriceExpected,
                springSeasonDoubleRoomPriceExpected,
                springSeasonJuniorSuitePriceExpected,
                springSeasonSuitePriceExpected,
                summerSeasonSingleRoomPriceExpected,
                summerSeasonDoubleRoomPriceExpected,
                summerSeasonJuniorSuitePriceExpected,
                summerSeasonSuitePriceExpected,
                fallSeasonSingleRoomPriceExpected,
                fallSeasonDoubleRoomPriceExpected,
                fallSeasonJuniorSuitePriceExpected,
                fallSeasonSuitePriceExpected
        );

        Mockito.when(roomCategoryPriceRepository.allPrices()).thenReturn(seasonsWithPricesExpected);

        // when
        List<SeasonWithPricesDTO> seasonsWithPricesActual = seasonListingService.allSeasonsWithPrices();

        // then
        for (SeasonWithPricesDTO swp : seasonsWithPricesActual) {
            for (RoomCategoryPrice rcp : seasonsWithPricesExpected) {
                assertEquals(rcp.getSeason().getSeasonName().name(), swp.season());
                assertEquals(rcp.getSeason().getStartDate(), swp.startDate());
                assertEquals(rcp.getSeason().getEndDate(), swp.endDate());
                assertEquals(rcp.getRoomCategory().getRoomCategoryName().name(), swp.roomCategory());
                assertEquals(rcp.getPrice(), swp.price());
            }
        }
    }
}
