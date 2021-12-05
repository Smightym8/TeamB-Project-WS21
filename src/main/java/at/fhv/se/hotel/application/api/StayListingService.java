package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.dto.StayListingDTO;

import java.util.List;

public interface StayListingService {
    List<StayListingDTO> allStays();
}
