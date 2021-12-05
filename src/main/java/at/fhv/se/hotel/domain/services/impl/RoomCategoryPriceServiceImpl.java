package at.fhv.se.hotel.domain.services.impl;

import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryPrice;
import at.fhv.se.hotel.domain.model.roomcategory.Season;
import at.fhv.se.hotel.domain.repository.RoomCategoryPriceRepository;
import at.fhv.se.hotel.domain.services.api.RoomCategoryPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomCategoryPriceServiceImpl implements RoomCategoryPriceService {

    @Autowired
    private RoomCategoryPriceRepository roomCategoryPriceRepository;

    @Override
    public RoomCategoryPrice by(RoomCategory roomCategory, Season season) {
        return roomCategoryPriceRepository.priceBySeasonAndCategory(season, roomCategory.getRoomCategoryId())
                .get();
    }
}
