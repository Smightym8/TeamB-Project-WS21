package at.fhv.se.hotel.integration.infrastructure;

import at.fhv.se.hotel.domain.model.roomcategory.*;
import at.fhv.se.hotel.domain.repository.RoomCategoryPriceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class RoomCategoryPriceRepositoryImplTests {

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

        RoomCategoryPrice priceExpected = RoomCategoryPrice.create(
                roomCategoryPriceRepository.nextIdentity(),
                Season.WINTER,
                singleRoom,
                new BigDecimal("300")
        );

        // when
        roomCategoryPriceRepository.add(priceExpected);
        em.flush();

        RoomCategoryPrice priceActual = roomCategoryPriceRepository.priceBySeasonAndCategory(
                Season.WINTER, singleRoom.getRoomCategoryId()
        ).get();

        // then
        assertEquals(priceExpected.getPrice(), priceActual.getPrice());

    }
}
