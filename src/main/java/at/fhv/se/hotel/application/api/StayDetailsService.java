package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.api.exception.StayNotFoundException;
import at.fhv.se.hotel.application.dto.StayDetailsDTO;
import at.fhv.se.hotel.application.impl.StayDetailsServiceImpl;

/**
 * This class represents an interface that defines the functionality to get details of a stay.
 * The implementation is in {@link StayDetailsServiceImpl}
 */
public interface StayDetailsService {
    /**
     * See implementation {@link StayDetailsServiceImpl#detailsById(String)}
     */
    StayDetailsDTO detailsById(String stayId) throws StayNotFoundException;
}
