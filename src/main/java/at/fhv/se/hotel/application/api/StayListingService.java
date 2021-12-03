package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.dto.StayDTO;

import java.util.List;

public interface StayListingService {
    List<StayDTO> allStays();
}
