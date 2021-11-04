package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.BookingCreationService;
import at.fhv.se.hotel.application.api.GuestListingService;
import at.fhv.se.hotel.application.dto.GuestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookingCreationServiceImpl implements BookingCreationService {
    @Autowired
    GuestListingService guestListingService;

    private GuestDTO guest;

    @Override
    public void addGuest(String id) {
        this.guest = guestListingService.findGuestById(id);
    }

    @Override
    public GuestDTO getGuestInBooking() {
        return this.guest;
    }
}
