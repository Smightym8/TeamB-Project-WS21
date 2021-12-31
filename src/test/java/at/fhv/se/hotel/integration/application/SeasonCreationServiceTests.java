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

        String singleRoomName = "Single Room";
        String doubleRoomName = "Double Room";
        String juniorSuiteName = "Junior Suite";
        String suiteName = "Suite";

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

        Mockito.when(roomCategoryRepository.roomCategoryByName(singleRoomName)).thenReturn(singleRoomExpected);
        Mockito.when(roomCategoryRepository.roomCategoryByName(doubleRoomName)).thenReturn(doubleRoomExpected);
        Mockito.when(roomCategoryRepository.roomCategoryByName(juniorSuiteName)).thenReturn(juniorSuiteExpected);
        Mockito.when(roomCategoryRepository.roomCategoryByName(suiteName)).thenReturn(suiteExpected);

        RoomCategoryPriceId singleRoomPriceIdExpected = new RoomCategoryPriceId("1");
        RoomCategoryPrice singleRoomPriceExpected = RoomCategoryPrice.create(
                singleRoomPriceIdExpected,
                seasonExpected,
                roomCategoryRepository.roomCategoryByName("Single Room"),
                singleRoomPriceExpectedBigDecimal
        );

        RoomCategoryPriceId doubleRoomPriceIdExpected = new RoomCategoryPriceId("2");
        RoomCategoryPrice doubleRoomPriceExpected = RoomCategoryPrice.create(
                doubleRoomPriceIdExpected,
                seasonExpected,
                roomCategoryRepository.roomCategoryByName("Double Room"),
                doubleRoomPriceExpectedBigDecimal
        );

        RoomCategoryPriceId juniorSuitePriceIdExpected = new RoomCategoryPriceId("3");
        RoomCategoryPrice juniorSuitePriceExpected = RoomCategoryPrice.create(
                juniorSuitePriceIdExpected,
                seasonExpected,
                roomCategoryRepository.roomCategoryByName("Junior Suite"),
                juniorSuitePriceExpectedBigDecimal
        );

        RoomCategoryPriceId suitePriceIdExpected = new RoomCategoryPriceId("4");
        RoomCategoryPrice suitePriceExpected = RoomCategoryPrice.create(
                suitePriceIdExpected,
                seasonExpected,
                roomCategoryRepository.roomCategoryByName("Suite"),
                suitePriceExpectedBigDecimal
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

        roomCategoryPriceRepository.add(singleRoomPriceExpected);
        ArgumentCaptor<RoomCategoryPrice> singleRoomCaptor = ArgumentCaptor.forClass(RoomCategoryPrice.class);
        Mockito.verify(roomCategoryPriceRepository).add(singleRoomCaptor.capture());
        RoomCategoryPrice singleRoomPriceActual = singleRoomCaptor.getValue();

        roomCategoryPriceRepository.add(doubleRoomPriceExpected);
        ArgumentCaptor<RoomCategoryPrice> doubleRoomCaptor = ArgumentCaptor.forClass(RoomCategoryPrice.class);
        Mockito.verify(roomCategoryPriceRepository).add(doubleRoomCaptor.capture());
        RoomCategoryPrice doubleRoomPriceActual = doubleRoomCaptor.getValue();

        roomCategoryPriceRepository.add(juniorSuitePriceExpected);
        ArgumentCaptor<RoomCategoryPrice> juniorSuiteCaptor = ArgumentCaptor.forClass(RoomCategoryPrice.class);
        Mockito.verify(roomCategoryPriceRepository).add(juniorSuiteCaptor.capture());
        RoomCategoryPrice juniorSuitePriceActual = juniorSuiteCaptor.getValue();

        roomCategoryPriceRepository.add(suitePriceExpected);
        ArgumentCaptor<RoomCategoryPrice> suiteCaptor = ArgumentCaptor.forClass(RoomCategoryPrice.class);
        Mockito.verify(roomCategoryPriceRepository).add(suiteCaptor.capture());
        RoomCategoryPrice suitePriceActual = suiteCaptor.getValue();

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
        assertEquals(singleRoomPriceExpected.getRoomCategory()., singleRoomPriceActual.getRoomCategory().getRoomCategoryName().name());

        assertEquals(doubleRoomPriceExpected.getPrice(), doubleRoomPriceActual.getPrice());
        assertEquals(doubleRoomPriceExpected.getRoomCategoryPriceId().id(), doubleRoomPriceActual.getRoomCategoryPriceId().id());
        assertEquals(doubleRoomPriceExpected.getSeason().getSeasonName(), doubleRoomPriceActual.getSeason().getSeasonName());
        assertEquals(doubleRoomPriceExpected.getSeason().getStartDate(), doubleRoomPriceActual.getSeason().getStartDate());
        assertEquals(doubleRoomPriceExpected.getSeason().getEndDate(), doubleRoomPriceActual.getSeason().getEndDate());
        assertEquals(doubleRoomPriceExpected.getRoomCategory().getRoomCategoryId().id(), doubleRoomPriceActual.getRoomCategory().getRoomCategoryId().id());
        assertEquals(doubleRoomPriceExpected.getRoomCategory().getRoomCategoryName().name(), doubleRoomPriceActual.getRoomCategory().getRoomCategoryName().name());
        assertEquals(doubleRoomPriceExpected.getRoomCategory().getDescription().description(), doubleRoomPriceActual.getRoomCategory().getDescription().description());
        assertEquals(doubleRoomPriceExpected.getRoomCategory()., doubleRoomPriceActual.getRoomCategory().getRoomCategoryName().name());

    }
}
