package at.fhv.se.hotel.infrastructure;

import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.model.stay.StayId;
import at.fhv.se.hotel.domain.repository.StayRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<Stay> stayByCheckout(LocalDate checkOutDate) {
        TypedQuery<Stay> query = this.em.createQuery("FROM Stay AS s WHERE s.booking.checkOutDate = :checkOutDate", Stay.class);
        query.setParameter("checkOutDate", checkOutDate);
        return query.getResultList();
    }
}
