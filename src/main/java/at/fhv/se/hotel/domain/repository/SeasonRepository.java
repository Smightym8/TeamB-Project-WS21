package at.fhv.se.hotel.domain.repository;

import at.fhv.se.hotel.domain.model.season.Season;
import at.fhv.se.hotel.domain.model.season.SeasonId;

import java.util.List;

public interface SeasonRepository {
    List<Season> findAllSeasons();

    SeasonId nextIdentity();

    void add(Season s);
}
