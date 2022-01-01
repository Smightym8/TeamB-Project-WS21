package at.fhv.se.hotel.integration.domain;

import at.fhv.se.hotel.domain.model.roomcategory.*;
import at.fhv.se.hotel.domain.model.season.Season;
import at.fhv.se.hotel.domain.model.season.SeasonId;
import at.fhv.se.hotel.domain.model.season.SeasonName;
import at.fhv.se.hotel.domain.repository.RoomCategoryPriceRepository;
import at.fhv.se.hotel.domain.services.api.RoomCategoryPriceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CategoryPriceServiceTests {

    @Autowired
    private RoomCategoryPriceService roomCategoryPriceService;

    @MockBean
    RoomCategoryPriceRepository roomCategoryPriceRepository;

    @Test
    void given_categoryandseason_then_expectedprice() {
        // given
        Mockito.when(roomCategoryPriceRepository.nextIdentity())
                .thenReturn(new RoomCategoryPriceId(UUID.randomUUID().toString().toUpperCase()));

        RoomCategory singleRoom = RoomCategory.create(new RoomCategoryId(UUID.randomUUID().toString().toUpperCase()),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        Season season = Season.create(
                new SeasonId("1"),
                new SeasonName("Winter"),
                LocalDate.of(2021, 12, 1),
                LocalDate.of(2022, 1, 31)
        );

        RoomCategoryPrice priceExpected = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                season,
                singleRoom,
                new BigDecimal("300")
        );

        Mockito.when(roomCategoryPriceRepository.priceBySeasonAndCategory(season.getSeasonId(), singleRoom.getRoomCategoryId()))
                .thenReturn(Optional.of(priceExpected));

        // when
        RoomCategoryPrice priceActual = roomCategoryPriceService.by(singleRoom, season.getSeasonId());

        // then
        assertEquals(priceExpected, priceActual);
        assertEquals(priceExpected.getRoomCategoryPriceId(), priceActual.getRoomCategoryPriceId());
        assertEquals(priceExpected.getPrice(), priceActual.getPrice());
        assertEquals(priceExpected.getRoomCategory(), priceActual.getRoomCategory());
        assertEquals(priceExpected.getSeason(), priceActual.getSeason());
    }
}