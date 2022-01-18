package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.api.exception.GuestNotFoundException;
import at.fhv.se.hotel.application.dto.GuestDetailsDTO;
import at.fhv.se.hotel.application.dto.GuestListingDTO;
import at.fhv.se.hotel.application.impl.GuestListingServiceImpl;

import java.util.List;

/**
 * This class represents an interface that defines the functionality to get all guests.
 * The implementation is in {@link GuestListingServiceImpl}
 */
public interface GuestListingService {

    /**
     * See implementation {@link GuestListingServiceImpl#allGuests()}
     */
    List<GuestListingDTO> allGuests();

    /**
     * See implementation {@link GuestListingServiceImpl#findGuestById(String)}}
     */
    GuestDetailsDTO findGuestById(String id) throws GuestNotFoundException;
}
