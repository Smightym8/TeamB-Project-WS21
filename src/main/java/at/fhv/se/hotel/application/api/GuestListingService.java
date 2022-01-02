package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.api.exception.GuestNotFoundException;
import at.fhv.se.hotel.application.dto.GuestDetailsDTO;
import at.fhv.se.hotel.application.dto.GuestListingDTO;

import java.util.List;
import java.util.Optional;

public interface GuestListingService {
    List<GuestListingDTO> allGuests();
    GuestDetailsDTO findGuestById(String id) throws GuestNotFoundException;
}
