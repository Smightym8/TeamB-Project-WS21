package at.fhv.se.hotel.domain.repository;

import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.service.ServiceId;
import at.fhv.se.hotel.infrastructure.HibernateRoomCategoryRepository;
import at.fhv.se.hotel.infrastructure.HibernateServiceRepository;

import java.util.List;
import java.util.Optional;

/**
 * This class is an interface that defines the functionality to persist and get service objects from the database.
 * The implementation is in {@link at.fhv.se.hotel.infrastructure.HibernateServiceRepository}
 */
public interface ServiceRepository {

    /**
     * See implementation
     * {@link HibernateServiceRepository#findAllServices()}
     */
    List<Service> findAllServices();

    /**
     * See implementation
     * {@link HibernateServiceRepository#nextIdentity()}
     */
    ServiceId nextIdentity();

    /**
     * See implementation
     * {@link HibernateServiceRepository#add(Service)}
     */
    void add(Service service);

    /**
     * See implementation
     * {@link HibernateServiceRepository#serviceById(ServiceId)}
     */
    Optional<Service> serviceById(ServiceId serviceId);
}
