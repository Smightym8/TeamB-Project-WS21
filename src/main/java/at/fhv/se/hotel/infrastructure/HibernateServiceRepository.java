package at.fhv.se.hotel.infrastructure;

import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.service.ServiceId;
import at.fhv.se.hotel.domain.repository.ServiceRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class HibernateServiceRepository implements ServiceRepository {
    @Override
    public List<Service> findAllServices() {
        return null;
    }

    @Override
    public ServiceId nextIdentity() {
        return new ServiceId(UUID.randomUUID().toString().toUpperCase());
    }

    @Override
    public void add(Service service) {

    }

    @Override
    public Optional<Service> serviceById(ServiceId serviceId) {
        return Optional.empty();
    }
}
