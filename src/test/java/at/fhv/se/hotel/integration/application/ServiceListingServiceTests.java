package at.fhv.se.hotel.integration.application;

import at.fhv.se.hotel.application.api.ServiceListingService;
import at.fhv.se.hotel.application.dto.ServiceDTO;
import at.fhv.se.hotel.domain.model.service.Price;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.service.ServiceId;
import at.fhv.se.hotel.domain.model.service.ServiceName;
import at.fhv.se.hotel.domain.repository.ServiceRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ServiceListingServiceTests {
    @Autowired
    private ServiceListingService serviceListingService;

    @MockBean
    private ServiceRepository serviceRepository;

    @Test
    void given_2servicesinrepository_when_fetchingallservices_then_2matchingservices(){
        //given
        Mockito.when(serviceRepository.nextIdentity()).thenReturn(
                new ServiceId(UUID.randomUUID().toString().toUpperCase()));

        List<Service> servicesExpected = List.of(
                Service.create(
                        serviceRepository.nextIdentity(),
                        new ServiceName("TV"),
                        new Price(new BigDecimal("100"))),
                Service.create(
                        serviceRepository.nextIdentity(),
                        new ServiceName("Breakfast"),
                        new Price(new BigDecimal("100"))));
        Mockito.when(serviceRepository.findAllServices()).thenReturn(servicesExpected);

        //when
        List<ServiceDTO> servicesActual = serviceListingService.allServices();

        //then
        assertEquals(servicesExpected.size(), servicesActual.size());
        for (int i = 0; i < servicesActual.size(); i ++){
            assertEquals(servicesExpected.get(i).getServiceId().id(), servicesActual.get(i).id());
            assertEquals(servicesExpected.get(i).getServiceName().name(), servicesActual.get(i).name());
            assertEquals(servicesExpected.get(i).getServicePrice().price(), servicesActual.get(i).price());
        }
    }

    @Test
    void given_serviceinrepository_when_fetchbyid_then_returnequalsservice() {
        // given
        ServiceId idExpected = new ServiceId("42");

        Service serviceExpected = Service.create(
                idExpected,
                new ServiceName("Breakfast"),
                new Price(new BigDecimal("100"))
        );

        Mockito.when(serviceRepository.serviceById(idExpected)).thenReturn(Optional.of(serviceExpected));

        // when
        ServiceDTO serviceActual = serviceListingService.findServiceById(idExpected.id()).get();

        // then
        assertEquals(idExpected.id(), serviceActual.id());
        assertEquals(serviceExpected.getServiceName().name(), serviceActual.name());
        assertEquals(serviceExpected.getServicePrice().price(), serviceActual.price());
    }
}
