package at.fhv.se.hotel.unit.domain;

import at.fhv.se.hotel.domain.model.roomcategory.*;
import at.fhv.se.hotel.domain.services.api.RoomCategoryPriceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CategoryPriceTests {

    @Autowired
    private RoomCategoryPriceService roomCategoryPriceService;

    // TODO Mock RoomCategoryRepo

    @Test
    void given_categoryandseason_then_expectedprice() {
        // given
        RoomCategory singleRoom = RoomCategory.create(new RoomCategoryId(UUID.randomUUID().toString().toUpperCase()),
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
