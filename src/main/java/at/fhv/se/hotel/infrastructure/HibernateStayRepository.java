package at.fhv.se.hotel.infrastructure;

import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.model.stay.StayId;
import at.fhv.se.hotel.domain.repository.StayRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class HibernateStayRepository implements StayRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Stay> findAllStays() {
        TypedQuery<Stay> query = this.em.createQuery("SELECT s FROM Stay s", Stay.class);
        return query.getResultList(); // to knallts
    }

    @Override
    public StayId nextIdentity() {
        return new StayId(UUID.randomUUID().toString().toUpperCase());
    }

    @Override
    public void add(Stay stay) {
        this.em.persist(stay);
        this.em.flush();
    }

    @Override
    public Optional<Stay> stayById(StayId stayId) {
        TypedQuery<Stay> query = this.em.createQuery("FROM Stay AS s WHERE s.stayId = :stayId", Stay.class);
        query.setParameter("stayId", stayId);
        return query.getResultList().stream().findFirst();
    }
}
