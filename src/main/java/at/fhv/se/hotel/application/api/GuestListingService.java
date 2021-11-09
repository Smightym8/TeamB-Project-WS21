package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.dto.GuestDTO;

import java.util.List;
import java.util.Optional;

public interface GuestListingService {
    List<GuestDTO> allGuests();
    Optional<GuestDTO> findGuestById(String id);
    List<GuestDTO> findGuestByName(String name);
}