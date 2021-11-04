package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.dto.GuestDTO;

import java.util.List;

public interface GuestListingService {
    List<GuestDTO> allGuests();
    GuestDTO findGuestById(String id);
    List<GuestDTO> findGuestByName(String name);
}
