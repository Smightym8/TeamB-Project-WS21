package at.fhv.se.hotel.domain.services.api;

import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.Season;

import java.math.BigDecimal;

public interface RoomCategoryPriceService {
    BigDecimal by(RoomCategory r, Season s);
}
