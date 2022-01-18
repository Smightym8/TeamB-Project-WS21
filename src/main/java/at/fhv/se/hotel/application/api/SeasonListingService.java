package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.dto.SeasonWithPricesDTO;
import at.fhv.se.hotel.application.impl.SeasonListingServiceImpl;

import java.util.List;

/**
 * This class represents an interface that defines the functionality to get all seasons with prices.
 * The implementation is in {@link SeasonListingServiceImpl}
 */
public interface SeasonListingService {

    /**
     * See implementation {@link SeasonListingServiceImpl#allSeasonsWithPrices()}
     */
    List<SeasonWithPricesDTO> allSeasonsWithPrices();
}
