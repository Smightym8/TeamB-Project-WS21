package at.fhv.se.hotel.domain.repository;

import at.fhv.se.hotel.domain.model.guest.Guest;
import at.fhv.se.hotel.domain.model.guest.GuestId;

import java.util.List;
import java.util.Optional;

public interface GuestRepository {
    List<Guest> findAllGuests();

    GuestId nextIdentity();

    void add(Guest guest);

    Optional<Guest> guestById(GuestId guestId);
}
