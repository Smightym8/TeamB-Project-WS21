package at.fhv.se.hotel.application.api;

import at.fhv.se.hotel.application.dto.GuestDTO;

import java.util.Optional;

public interface BookingCreationService {
    void addGuest(String id);

    GuestDTO getGuestInBooking();
}
