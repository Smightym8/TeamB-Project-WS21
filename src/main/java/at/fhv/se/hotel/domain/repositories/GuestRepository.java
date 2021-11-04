package at.fhv.se.hotel.domain.repositories;

import java.util.Optional;

public interface GuestRepository {
    List<Guest> findAllGuests();

    GuestId nextIdentity();

    void add(Guest guest);

    void remove(Guest guest);

    Optional<Guest> guestById(GuestId guestId);
}
