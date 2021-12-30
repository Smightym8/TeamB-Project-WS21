package at.fhv.se.hotel.infrastructure;

import at.fhv.se.hotel.domain.model.season.Season;
import at.fhv.se.hotel.domain.model.season.SeasonId;
import at.fhv.se.hotel.domain.repository.SeasonRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class HibernateSeasonRepositoryImpl implements SeasonRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Season> findAllSeasons() {
        TypedQuery<Season> query = this.em.createQuery("SELECT s FROM Season s", Season.class);
        return query.getResultList();
    }

    @Override
    public SeasonId nextIdentity() {
        return new SeasonId(UUID.randomUUID().toString().toUpperCase());
    }

    @Override
    public void add(Season s) {
        this.em.persist(s);
    }

    @Override
    public Optional<Season> seasonByDate(LocalDate date) {
        TypedQuery<Season> query = this.em.createQuery(
                "FROM Season AS s WHERE :date BETWEEN s.startDate AND s.endDate", Season.class
        );

        query.setParameter("date", date);

        return query.getResultList().stream().findFirst();
    }
}
