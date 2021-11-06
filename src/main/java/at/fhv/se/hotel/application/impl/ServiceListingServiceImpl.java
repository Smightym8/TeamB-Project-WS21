package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.ServiceListingService;
import at.fhv.se.hotel.application.dto.ServiceDTO;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceListingServiceImpl implements ServiceListingService {

    @Autowired
    ServiceRepository serviceRepository;

    @Transactional(readOnly=true)
    @Override
    public List<ServiceDTO> allServices() {
        List<Service> services = serviceRepository.findAllServices();
        List<ServiceDTO> dtos = new ArrayList<>();

        for(Service service : services) {
            ServiceDTO dto = ServiceDTO.builder()
                    .withId(service.getServiceId().id())
                    .withName(service.getServiceName().name())
                    .build();

            dtos.add(dto);
        }

        return dtos;
    }
}
