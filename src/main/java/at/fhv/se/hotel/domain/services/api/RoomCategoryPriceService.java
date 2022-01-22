package at.fhv.se.hotel.domain.services.api;

import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryPrice;
import at.fhv.se.hotel.domain.model.season.SeasonId;


/**
 * This class is an interface that defines the functionality to get a Room Category price by category and season.
 * The implementation is in {@link at.fhv.se.hotel.domain.services.impl.RoomCategoryPriceServiceImpl}
 */
public interface RoomCategoryPriceService {
    /**
     * See implementation
     * {@link at.fhv.se.hotel.domain.services.impl.RoomCategoryPriceServiceImpl#by(RoomCategory, SeasonId)}
     */
    RoomCategoryPrice by(RoomCategory roomCategory, SeasonId seasonId);
}
