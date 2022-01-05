package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.SeasonListingService;
import at.fhv.se.hotel.application.dto.SeasonWithPricesDTO;
import at.fhv.se.hotel.domain.repository.RoomCategoryPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeasonListingServiceImpl implements SeasonListingService {
    @Autowired
    RoomCategoryPriceRepository roomCategoryPriceRepository;

    @Override
    public List<SeasonWithPricesDTO> allSeasonsWithPrices() {
        return null;
    }
}
