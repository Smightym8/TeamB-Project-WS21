package at.fhv.se.hotel.domain.repository;

import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.service.ServiceId;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository {
    List<Service> findAllServices();

    ServiceId nextIdentity();

    void add(Service service);

    Optional<Service> serviceById(ServiceId serviceId);
}
