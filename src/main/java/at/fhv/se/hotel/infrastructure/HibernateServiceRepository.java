package at.fhv.se.hotel.infrastructure;

import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.service.ServiceId;
import at.fhv.se.hotel.domain.repository.ServiceRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class HibernateServiceRepository implements ServiceRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Service> findAllServices() {
        TypedQuery<Service> query = this.em.createQuery("SELECT s from Service s", Service.class);
        return query.getResultList();
    }

    @Override
    public ServiceId nextIdentity() {
        return new ServiceId(UUID.randomUUID().toString().toUpperCase());
    }

    @Override
    public void add(Service service) {
        this.em.persist(service);
        this.em.flush();
    }

    @Override
    public Optional<Service> serviceById(ServiceId serviceId) {
        TypedQuery<Service> query = this.em.createQuery("FROM Service AS s WHERE s.serviceId = :serviceId", Service.class);
        query.setParameter("serviceId", serviceId);
        return singleResultOptional(query);
    }

    private static <T> Optional<T> singleResultOptional(TypedQuery<T> query) {
        // NOTE: getSingleResult throws an error if there is none
        List<T> result = query.getResultList();
        if (1 != result.size()) {
            return Optional.empty();
        }

        return Optional.of(result.get(0));
    }
}