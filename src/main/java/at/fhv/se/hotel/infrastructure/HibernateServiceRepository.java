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

/**
 * This class represents the implementation of the interface {@link ServiceRepository}.
 * It provides the functionality to
 * get all services
 * generate a new service id
 * save a service in the database
 * get a service by id
 */
@Component
public class HibernateServiceRepository implements ServiceRepository {
    @PersistenceContext
    private EntityManager em;

    /**
     * This method gets all services from the database
     * @return a list with all services
     */
    @Override
    public List<Service> findAllServices() {
        TypedQuery<Service> query = this.em.createQuery("SELECT s from Service s", Service.class);
        return query.getResultList();
    }

    /**
     * This method generates a new service id
     * @return the generated service id
     */
    @Override
    public ServiceId nextIdentity() {
        return new ServiceId(UUID.randomUUID().toString().toUpperCase());
    }

    /**
     * This method saves a service in the database
     * @param service contains the service which will be saved
     */
    @Override
    public void add(Service service) {
        this.em.persist(service);
    }

    /**
     * This method gets a service by id from the database
     * @param serviceId contains the id of the service
     * @return the service with the provided id
     */
    @Override
    public Optional<Service> serviceById(ServiceId serviceId) {
        TypedQuery<Service> query = this.em.createQuery("FROM Service AS s WHERE s.serviceId = :serviceId", Service.class);
        query.setParameter("serviceId", serviceId);
        return query.getResultStream().findFirst();
    }
}
