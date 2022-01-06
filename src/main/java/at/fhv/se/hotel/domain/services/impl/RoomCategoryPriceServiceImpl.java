package at.fhv.se.hotel.domain.services.impl;

import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryPrice;
import at.fhv.se.hotel.domain.model.season.SeasonId;
import at.fhv.se.hotel.domain.repository.RoomCategoryPriceRepository;
import at.fhv.se.hotel.domain.services.api.RoomCategoryPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomCategoryPriceServiceImpl implements RoomCategoryPriceService {

    @Autowired
    private RoomCategoryPriceRepository roomCategoryPriceRepository;

    @Override
    public RoomCategoryPrice by(RoomCategory roomCategory, SeasonId seasonId) {
        return roomCategoryPriceRepository.priceBySeasonAndCategory(seasonId, roomCategory.getRoomCategoryId())
                .get();
    }
}
