package at.fhv.se.hotel.integration.infrastructure;

import at.fhv.se.hotel.domain.model.service.Price;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.service.ServiceId;
import at.fhv.se.hotel.domain.model.service.ServiceName;
import at.fhv.se.hotel.domain.repository.ServiceRepository;
import at.fhv.se.hotel.infrastructure.HibernateServiceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class ServiceRepositoryImplTests {
    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private EntityManager em;

    @Test
    void given_service_when_addservicerepository_then_returnequalsservice() {
        // given
        ServiceId idExpected = new ServiceId("42");
        Service serviceExpected = Service.create(idExpected, new ServiceName("TV"),
                new Price(new BigDecimal("42")));

        // when
        this.serviceRepository.add(serviceExpected);
        em.flush();
        Service serviceActual = this.serviceRepository.serviceById(idExpected).get();

        // then
        assertEquals(serviceExpected, serviceActual);
        assertEquals(serviceExpected.getServiceId(), serviceActual.getServiceId());
    }

    @Test
    void given_3services_when_addservicesrepository_then_return3equalsservices() {
        // given
        ServiceId idExpected1 = new ServiceId("1");
        Service serviceExpected1 = Service.create(idExpected1, new ServiceName("TV"),
                new Price(new BigDecimal("20")));

        ServiceId idExpected2 = new ServiceId("2");
        Service serviceExpected2 = Service.create(idExpected2, new ServiceName("Breakfast"),
                new Price(new BigDecimal("30")));

        ServiceId idExpected3 = new ServiceId("3");
        Service serviceExpected3 = Service.create(idExpected3, new ServiceName("Internet"),
                new Price(new BigDecimal("40")));

        List<Service> servicesExpected = Arrays.asList(
                serviceExpected1,
                serviceExpected2,
                serviceExpected3
        );

        // when
        this.serviceRepository.add(serviceExpected1);
        this.serviceRepository.add(serviceExpected2);
        this.serviceRepository.add(serviceExpected3);
        em.flush();

        // then
        List<Service> servicesActual = this.serviceRepository.findAllServices();

        assertEquals(servicesExpected.size(), servicesActual.size());

        for (int i = 0; i < 3; i++) {
            assertEquals(servicesExpected.get(i).getServiceId(), servicesActual.get(i).getServiceId());
            assertEquals(servicesExpected.get(i).getServiceName(), servicesActual.get(i).getServiceName());
            assertEquals(servicesExpected.get(i).getServicePrice(), servicesActual.get(i).getServicePrice());
        }
    }
}
