package at.fhv.se.hotel.domain.services.impl;

import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.Season;
import at.fhv.se.hotel.domain.services.api.RoomCategoryPriceService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class RoomCategoryPriceServiceImpl implements RoomCategoryPriceService {

    @Override
    public BigDecimal by(RoomCategory r, Season s) {
        return new BigDecimal("300");
    }
}
