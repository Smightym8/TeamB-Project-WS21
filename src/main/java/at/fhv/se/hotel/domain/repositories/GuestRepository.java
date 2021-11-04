package at.fhv.se.hotel.domain.repositories;

import at.fhv.se.hotel.domain.models.Guest.Guest;
import at.fhv.se.hotel.domain.models.Guest.GuestId;

import java.util.List;
import java.util.Optional;

public interface GuestRepository {
    List<Guest> findAllGuests();

    GuestId nextIdentity();

    void add(Guest guest);

    void remove(Guest guest);

    Optional<Guest> guestById(GuestId guestId);
}
