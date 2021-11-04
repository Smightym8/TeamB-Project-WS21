package at.fhv.se.hotel.domain.repositories;

import java.util.Optional;

public interface ServiceRepository {
    List<Service> findAllServices();

    ServiceId nextIdentity();

    void add(Service service);

    Optional<Service> serviceById(ServiceId serviceId);
}
