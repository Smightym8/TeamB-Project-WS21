package at.fhv.se.hotel.domain.repository;

import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.model.stay.StayId;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StayRepository {
    List<Stay> findAllStays();

    StayId nextIdentity();

    void add(Stay stay);

    Optional<Stay> stayById(StayId stayId);
}
