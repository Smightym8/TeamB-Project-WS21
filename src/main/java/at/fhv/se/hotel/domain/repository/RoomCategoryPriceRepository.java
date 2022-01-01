package at.fhv.se.hotel.domain.repository;

import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryPrice;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryPriceId;
import at.fhv.se.hotel.domain.model.season.SeasonId;

import java.util.Optional;

public interface RoomCategoryPriceRepository {
    RoomCategoryPriceId nextIdentity();

    void add(RoomCategoryPrice roomCategoryPrice);

    Optional<RoomCategoryPrice> priceBySeasonAndCategory(SeasonId seasonId, RoomCategoryId roomCategoryId);
}
