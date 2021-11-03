package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.ServiceListingService;
import at.fhv.se.hotel.application.dto.ServiceDTO;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

@Component
public class ServiceListingServiceImpl implements ServiceListingService {

    @Override
    public List<ServiceDTO> allServices() {
        final List<ServiceDTO> services = Arrays.asList(
                ServiceDTO.builder()
                        .withId("1")
                        .withName("TV")
                        .build(),
                ServiceDTO.builder()
                        .withId("2")
                        .withName("Breakfast")
                        .build()
        );
        return services;
    }
}
