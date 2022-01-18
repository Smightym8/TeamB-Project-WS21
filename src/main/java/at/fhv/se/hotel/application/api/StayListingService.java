package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.dto.StayListingDTO;
import at.fhv.se.hotel.application.impl.StayListingServiceImpl;

import java.util.List;

/**
 * This class represents an interface that defines the functionality to get all stays.
 * The implementation is in {@link StayListingServiceImpl}
 */
public interface StayListingService {
    /**
     * See implementation {@link StayListingServiceImpl#allStays()}
     */
    List<StayListingDTO> allStays();
}
