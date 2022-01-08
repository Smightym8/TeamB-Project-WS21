package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.dto.SeasonWithPricesDTO;

import java.util.List;

public interface SeasonListingService {
    List<SeasonWithPricesDTO> allSeasonsWithPrices();
}
