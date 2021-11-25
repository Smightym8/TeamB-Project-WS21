package at.fhv.se.hotel.unit.domain;

import at.fhv.se.hotel.domain.model.roomcategory.*;
import at.fhv.se.hotel.domain.repository.RoomCategoryRepository;
import at.fhv.se.hotel.domain.services.api.RoomCategoryPriceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CategoryPriceTests {

    @Autowired
    private RoomCategoryPriceService roomCategoryPriceService;

    @MockBean
    private RoomCategoryRepository roomCategoryRepository;

    @Test
    void given_categoryandseason_then_expectedprice() {
        // given
        Mockito.when(roomCategoryRepository.nextIdentity()).thenReturn(new RoomCategoryId(UUID.randomUUID().toString().toUpperCase()));

        RoomCategory singleRoom = RoomCategory.create(roomCategoryRepository.nextIdentity(),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        Season season = Season.WINTER;
        BigDecimal priceExpected = new BigDecimal("300");

        // when ... then
        BigDecimal priceActual = roomCategoryPriceService.by(singleRoom, season);
        assertEquals(priceExpected, priceActual);

    }
}
