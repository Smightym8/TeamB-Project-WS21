package at.fhv.se.hotel.infrastructure;

import at.fhv.se.hotel.domain.model.season.Season;
import at.fhv.se.hotel.domain.model.season.SeasonId;
import at.fhv.se.hotel.domain.repository.RoomRepository;
import at.fhv.se.hotel.domain.repository.SeasonRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * This class represents the implementation of the interface {@link SeasonRepository}.
 * It provides the functionality to
 * get all seasons
 * generate a new season id
 * save a season in the database
 * get a season by date
 */
@Component
public class HibernateSeasonRepository implements SeasonRepository {
    @PersistenceContext
    private EntityManager em;

    /**
     * This method gets all seasons from the database
     * @return a list with all seasons
     */
    @Override
    public List<Season> findAllSeasons() {
        TypedQuery<Season> query = this.em.createQuery("SELECT s FROM Season s", Season.class);
        return query.getResultList();
    }

    /**
     * This method generates a new season id
     * @return the generated id
     */
    @Override
    public SeasonId nextIdentity() {
        return new SeasonId(UUID.randomUUID().toString().toUpperCase());
    }

    /**
     * This method saves a season in the database
     * @param s contains the season which will be saved
     */
    @Override
    public void add(Season s) {
        this.em.persist(s);
    }

    /**
     * This method gets a seasons by date
     * @param date contains the date of the seasin
     * @return the season with start and end date where the provided date is between
     */
    @Override
    public Optional<Season> seasonByDate(LocalDate date) {
        TypedQuery<Season> query = this.em.createQuery(
                "FROM Season AS s WHERE :date BETWEEN s.startDate AND s.endDate", Season.class
        );

        query.setParameter("date", date);

        return query.getResultList().stream().findFirst();
    }
}
