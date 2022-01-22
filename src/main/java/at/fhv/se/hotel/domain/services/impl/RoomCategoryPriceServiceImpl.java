package at.fhv.se.hotel.domain.services.impl;

import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryPrice;
import at.fhv.se.hotel.domain.model.season.SeasonId;
import at.fhv.se.hotel.domain.repository.RoomCategoryPriceRepository;
import at.fhv.se.hotel.domain.services.api.RoomCategoryPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class represents the implementation of the interface {@link RoomCategoryPriceService}.
 * It provides the functionality to get the price of a room category by season and category.
 */
@Component
public class RoomCategoryPriceServiceImpl implements RoomCategoryPriceService {

    @Autowired
    private RoomCategoryPriceRepository roomCategoryPriceRepository;

    /**
     * This method gets the price for a category and a season
     * @param roomCategory contains the category for which a price is requests
     * @param seasonId contains the season which determines the price
     * @return the price of the room category for a specific season
     */
    @Override
    public RoomCategoryPrice by(RoomCategory roomCategory, SeasonId seasonId) {
        return roomCategoryPriceRepository.priceBySeasonAndCategory(seasonId, roomCategory.getRoomCategoryId())
                .get();
    }
}
