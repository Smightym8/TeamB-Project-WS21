package at.fhv.se.hotel.integration.application;

import at.fhv.se.hotel.application.api.SeasonCreationService;
import at.fhv.se.hotel.domain.model.roomcategory.*;
import at.fhv.se.hotel.domain.model.season.Season;
import at.fhv.se.hotel.domain.model.season.SeasonId;
import at.fhv.se.hotel.domain.model.season.SeasonName;
import at.fhv.se.hotel.domain.repository.RoomCategoryPriceRepository;
import at.fhv.se.hotel.domain.repository.RoomCategoryRepository;
import at.fhv.se.hotel.domain.repository.SeasonRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SeasonCreationServiceTests {
    @Autowired
    SeasonCreationService seasonCreationService;

    @MockBean
    SeasonRepository seasonRepository;

    @MockBean
    RoomCategoryRepository roomCategoryRepository;

    @MockBean
    RoomCategoryPriceRepository roomCategoryPriceRepository;

    @Test
    public void given_season_details_when_create_season_then_season_with_matching_details_created() {
        // given
        String seasonNameExpectedStr = "Summer Season";
        LocalDate startDateExpected = LocalDate.of(2021,7,1);
        LocalDate endDateExpected = LocalDate.of(2021, 9, 30);
        BigDecimal singleRoomPriceExpectedBigDecimal = new BigDecimal("20");
        BigDecimal doubleRoomPriceExpectedBigDecimal = new BigDecimal("40");
        BigDecimal juniorSuitePriceExpectedBigDecimal = new BigDecimal("80");
        BigDecimal suitePriceExpectedBigDecimal = new BigDecimal("150");

        SeasonId seasonIdExpected = new SeasonId("1");
        SeasonName seasonNameExpected = new SeasonName(seasonNameExpectedStr);
        Season seasonExpected = Season.create(
                seasonIdExpected,
                seasonNameExpected,
                startDateExpected,
                endDateExpected
        );

        RoomCategoryName singleRoomName = new RoomCategoryName("Single Room");
        RoomCategoryName doubleRoomName = new RoomCategoryName("Double Room");
        RoomCategoryName juniorSuiteName = new RoomCategoryName("Junior Suite");
        RoomCategoryName suiteName = new RoomCategoryName("Suite");

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

        Mockito.when(roomCategoryRepository.roomCategoryByName(singleRoomName)).thenReturn(Optional.of(singleRoomExpected));
        Mockito.when(roomCategoryRepository.roomCategoryByName(doubleRoomName)).thenReturn(Optional.of(doubleRoomExpected));
        Mockito.when(roomCategoryRepository.roomCategoryByName(juniorSuiteName)).thenReturn(Optional.of(juniorSuiteExpected));
        Mockito.when(roomCategoryRepository.roomCategoryByName(suiteName)).thenReturn(Optional.of(suiteExpected));

        RoomCategoryPriceId singleRoomPriceIdExpected = new RoomCategoryPriceId("1");
        RoomCategoryPrice singleRoomPriceExpected = RoomCategoryPrice.create(
                singleRoomPriceIdExpected,
                seasonExpected,
                roomCategoryRepository.roomCategoryByName(new RoomCategoryName("Single Room")).get(),
                singleRoomPriceExpectedBigDecimal
        );

        RoomCategoryPriceId doubleRoomPriceIdExpected = new RoomCategoryPriceId("2");
        RoomCategoryPrice doubleRoomPriceExpected = RoomCategoryPrice.create(
                doubleRoomPriceIdExpected,
                seasonExpected,
                roomCategoryRepository.roomCategoryByName(new RoomCategoryName("Double Room")).get(),
                doubleRoomPriceExpectedBigDecimal
        );

        RoomCategoryPriceId juniorSuitePriceIdExpected = new RoomCategoryPriceId("3");
        RoomCategoryPrice juniorSuitePriceExpected = RoomCategoryPrice.create(
                juniorSuitePriceIdExpected,
                seasonExpected,
                roomCategoryRepository.roomCategoryByName(new RoomCategoryName("Junior Suite")).get(),
                juniorSuitePriceExpectedBigDecimal
        );

        RoomCategoryPriceId suitePriceIdExpected = new RoomCategoryPriceId("4");
        RoomCategoryPrice suitePriceExpected = RoomCategoryPrice.create(
                suitePriceIdExpected,
                seasonExpected,
                roomCategoryRepository.roomCategoryByName(new RoomCategoryName("Suite")).get(),
                suitePriceExpectedBigDecimal
        );

        Mockito.when(seasonRepository.nextIdentity()).thenReturn(seasonIdExpected);

        Mockito.doNothing().when(seasonRepository).add(seasonExpected);
        Mockito.doNothing().when(roomCategoryPriceRepository).add(singleRoomPriceExpected);
        Mockito.doNothing().when(roomCategoryPriceRepository).add(doubleRoomPriceExpected);
        Mockito.doNothing().when(roomCategoryPriceRepository).add(juniorSuitePriceExpected);
        Mockito.doNothing().when(roomCategoryPriceRepository).add(suitePriceExpected);

        Mockito.when(roomCategoryPriceRepository.priceBySeasonAndCategory(seasonIdExpected, singleRoomExpected.getRoomCategoryId()))
                .thenReturn(Optional.of(singleRoomPriceExpected));
        Mockito.when(roomCategoryPriceRepository.priceBySeasonAndCategory(seasonIdExpected, doubleRoomExpected.getRoomCategoryId()))
                .thenReturn(Optional.of(doubleRoomPriceExpected));
        Mockito.when(roomCategoryPriceRepository.priceBySeasonAndCategory(seasonIdExpected, juniorSuiteExpected.getRoomCategoryId()))
                .thenReturn(Optional.of(juniorSuitePriceExpected));
        Mockito.when(roomCategoryPriceRepository.priceBySeasonAndCategory(seasonIdExpected, suiteExpected.getRoomCategoryId()))
                .thenReturn(Optional.of(suitePriceExpected));

        // when
        seasonCreationService.createSeason(
                seasonNameExpectedStr,
                startDateExpected,
                endDateExpected,
                singleRoomPriceExpectedBigDecimal,
                doubleRoomPriceExpectedBigDecimal,
                juniorSuitePriceExpectedBigDecimal,
                suitePriceExpectedBigDecimal
        );
        ArgumentCaptor<Season> seasonCaptor = ArgumentCaptor.forClass(Season.class);
        Mockito.verify(seasonRepository).add(seasonCaptor.capture());
        Season seasonActual = seasonCaptor.getValue();

        RoomCategoryPrice singleRoomPriceActual = roomCategoryPriceRepository.priceBySeasonAndCategory(seasonIdExpected, singleRoomExpected.getRoomCategoryId()).get();
        RoomCategoryPrice doubleRoomPriceActual = roomCategoryPriceRepository.priceBySeasonAndCategory(seasonIdExpected, doubleRoomExpected.getRoomCategoryId()).get();
        RoomCategoryPrice juniorSuitePriceActual = roomCategoryPriceRepository.priceBySeasonAndCategory(seasonIdExpected, juniorSuiteExpected.getRoomCategoryId()).get();
        RoomCategoryPrice suitePriceActual = roomCategoryPriceRepository.priceBySeasonAndCategory(seasonIdExpected, suiteExpected.getRoomCategoryId()).get();

        // then
        assertEquals(seasonExpected.getSeasonId().id(), seasonActual.getSeasonId().id());
        assertEquals(seasonExpected.getSeasonName().name(), seasonActual.getSeasonName().name());
        assertEquals(seasonExpected.getStartDate(), seasonActual.getStartDate());
        assertEquals(seasonExpected.getEndDate(), seasonActual.getEndDate());

        assertEquals(singleRoomPriceExpected.getPrice(), singleRoomPriceActual.getPrice());
        assertEquals(singleRoomPriceExpected.getRoomCategoryPriceId().id(), singleRoomPriceActual.getRoomCategoryPriceId().id());
        assertEquals(singleRoomPriceExpected.getSeason().getSeasonName(), singleRoomPriceActual.getSeason().getSeasonName());
        assertEquals(singleRoomPriceExpected.getSeason().getStartDate(), singleRoomPriceActual.getSeason().getStartDate());
        assertEquals(singleRoomPriceExpected.getSeason().getEndDate(), singleRoomPriceActual.getSeason().getEndDate());
        assertEquals(singleRoomPriceExpected.getRoomCategory().getRoomCategoryId().id(), singleRoomPriceActual.getRoomCategory().getRoomCategoryId().id());
        assertEquals(singleRoomPriceExpected.getRoomCategory().getRoomCategoryName().name(), singleRoomPriceActual.getRoomCategory().getRoomCategoryName().name());
        assertEquals(singleRoomPriceExpected.getRoomCategory().getDescription().description(), singleRoomPriceActual.getRoomCategory().getDescription().description());
        assertEquals(singleRoomPriceExpected.getRoomCategory().getRoomCategoryName().name(), singleRoomPriceActual.getRoomCategory().getRoomCategoryName().name());

        assertEquals(doubleRoomPriceExpected.getPrice(), doubleRoomPriceActual.getPrice());
        assertEquals(doubleRoomPriceExpected.getRoomCategoryPriceId().id(), doubleRoomPriceActual.getRoomCategoryPriceId().id());
        assertEquals(doubleRoomPriceExpected.getSeason().getSeasonName(), doubleRoomPriceActual.getSeason().getSeasonName());
        assertEquals(doubleRoomPriceExpected.getSeason().getStartDate(), doubleRoomPriceActual.getSeason().getStartDate());
        assertEquals(doubleRoomPriceExpected.getSeason().getEndDate(), doubleRoomPriceActual.getSeason().getEndDate());
        assertEquals(doubleRoomPriceExpected.getRoomCategory().getRoomCategoryId().id(), doubleRoomPriceActual.getRoomCategory().getRoomCategoryId().id());
        assertEquals(doubleRoomPriceExpected.getRoomCategory().getRoomCategoryName().name(), doubleRoomPriceActual.getRoomCategory().getRoomCategoryName().name());
        assertEquals(doubleRoomPriceExpected.getRoomCategory().getDescription().description(), doubleRoomPriceActual.getRoomCategory().getDescription().description());
        assertEquals(doubleRoomPriceExpected.getRoomCategory().getRoomCategoryName().name(), doubleRoomPriceActual.getRoomCategory().getRoomCategoryName().name());

        assertEquals(juniorSuitePriceExpected.getPrice(), juniorSuitePriceActual.getPrice());
        assertEquals(juniorSuitePriceExpected.getRoomCategoryPriceId().id(), juniorSuitePriceActual.getRoomCategoryPriceId().id());
        assertEquals(juniorSuitePriceExpected.getSeason().getSeasonName(), juniorSuitePriceActual.getSeason().getSeasonName());
        assertEquals(juniorSuitePriceExpected.getSeason().getStartDate(), juniorSuitePriceActual.getSeason().getStartDate());
        assertEquals(juniorSuitePriceExpected.getSeason().getEndDate(), juniorSuitePriceActual.getSeason().getEndDate());
        assertEquals(juniorSuitePriceExpected.getRoomCategory().getRoomCategoryId().id(), juniorSuitePriceActual.getRoomCategory().getRoomCategoryId().id());
        assertEquals(juniorSuitePriceExpected.getRoomCategory().getRoomCategoryName().name(), juniorSuitePriceActual.getRoomCategory().getRoomCategoryName().name());
        assertEquals(juniorSuitePriceExpected.getRoomCategory().getDescription().description(), juniorSuitePriceActual.getRoomCategory().getDescription().description());
        assertEquals(juniorSuitePriceExpected.getRoomCategory().getRoomCategoryName().name(), juniorSuitePriceActual.getRoomCategory().getRoomCategoryName().name());

        assertEquals(suitePriceExpected.getPrice(), suitePriceActual.getPrice());
        assertEquals(suitePriceExpected.getRoomCategoryPriceId().id(), suitePriceActual.getRoomCategoryPriceId().id());
        assertEquals(suitePriceExpected.getSeason().getSeasonName(), suitePriceActual.getSeason().getSeasonName());
        assertEquals(suitePriceExpected.getSeason().getStartDate(), suitePriceActual.getSeason().getStartDate());
        assertEquals(suitePriceExpected.getSeason().getEndDate(), suitePriceActual.getSeason().getEndDate());
        assertEquals(suitePriceExpected.getRoomCategory().getRoomCategoryId().id(), suitePriceActual.getRoomCategory().getRoomCategoryId().id());
        assertEquals(suitePriceExpected.getRoomCategory().getRoomCategoryName().name(), suitePriceActual.getRoomCategory().getRoomCategoryName().name());
        assertEquals(suitePriceExpected.getRoomCategory().getDescription().description(), suitePriceActual.getRoomCategory().getDescription().description());
        assertEquals(suitePriceExpected.getRoomCategory().getRoomCategoryName().name(), suitePriceActual.getRoomCategory().getRoomCategoryName().name());


    }
}
