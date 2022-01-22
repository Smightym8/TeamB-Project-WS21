package at.fhv.se.hotel.infrastructure;

import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.model.stay.StayId;
import at.fhv.se.hotel.domain.repository.RoomRepository;
import at.fhv.se.hotel.domain.repository.StayRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * This class represents the implementation of the interface {@link StayRepository}.
 * It provides the functionality to
 * get all stays
 * generate a new stay id
 * save a stay in the database
 * get a stay by id
 */
@Component
public class HibernateStayRepository implements StayRepository {
    @PersistenceContext
    private EntityManager em;

    /**
     * This method gets all stays from the database
     * @return
     */
    @Override
    public List<Stay> findAllStays() {
        TypedQuery<Stay> query = this.em.createQuery("SELECT s FROM Stay s", Stay.class);
        return query.getResultList(); // to knallts
    }

    /**
     * This method generates a new stay id
     * @return the generated id
     */
    @Override
    public StayId nextIdentity() {
        return new StayId(UUID.randomUUID().toString().toUpperCase());
    }

    /**
     * This method saves a stay in the database
     * @param stay contains the stay which will be saved
     */
    @Override
    public void add(Stay stay) {
        this.em.persist(stay);
    }

    /**
     * This method gets a stay by id from the database
     * @param stayId contains the id of the stay
     * @return the stay with the provided id
     */
    @Override
    public Optional<Stay> stayById(StayId stayId) {
        TypedQuery<Stay> query = this.em.createQuery("FROM Stay AS s WHERE s.stayId = :stayId", Stay.class);
        query.setParameter("stayId", stayId);
        return query.getResultList().stream().findFirst();
    }
}
