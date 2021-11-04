package at.fhv.se.hotel.domain.repositories;

import at.fhv.se.hotel.domain.models.Service.Service;
import at.fhv.se.hotel.domain.models.Service.ServiceId;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository {
    List<Service> findAllServices();

    ServiceId nextIdentity();

    void add(Service service);

    Optional<Service> serviceById(ServiceId serviceId);
}
