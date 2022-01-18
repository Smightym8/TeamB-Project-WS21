package at.fhv.se.hotel.domain.repository;

import at.fhv.se.hotel.domain.model.season.Season;
import at.fhv.se.hotel.domain.model.season.SeasonId;
import at.fhv.se.hotel.infrastructure.HibernateSeasonRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * This class is an interface that defines the functionality to persist and get season objects from the database.
 * The implementation is in {@link HibernateSeasonRepository}
 */
public interface SeasonRepository {

    /**
     * See implementation
     * {@link HibernateSeasonRepository#findAllSeasons()}
     */
    List<Season> findAllSeasons();

    /**
     * See implementation
     * {@link HibernateSeasonRepository#nextIdentity()}
     */
    SeasonId nextIdentity();

    /**
     * See implementation
     * {@link HibernateSeasonRepository#add(Season)}
     */
    void add(Season s);

    /**
     * See implementation
     * {@link HibernateSeasonRepository#seasonByDate(LocalDate)}
     */
    Optional<Season> seasonByDate(LocalDate date);
}
