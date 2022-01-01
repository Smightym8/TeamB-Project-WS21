package at.fhv.se.hotel.domain.repository;

import at.fhv.se.hotel.domain.model.season.Season;
import at.fhv.se.hotel.domain.model.season.SeasonId;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SeasonRepository {
    List<Season> findAllSeasons();

    SeasonId nextIdentity();

    void add(Season s);

    Optional<Season> seasonByDate(LocalDate date);
}
