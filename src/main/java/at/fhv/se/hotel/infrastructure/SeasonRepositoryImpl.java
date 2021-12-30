package at.fhv.se.hotel.infrastructure;

import at.fhv.se.hotel.domain.model.season.Season;
import at.fhv.se.hotel.domain.model.season.SeasonId;
import at.fhv.se.hotel.domain.repository.SeasonRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

@Component
public class SeasonRepositoryImpl implements SeasonRepository {
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
}
