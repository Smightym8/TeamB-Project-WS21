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

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class ServiceRepositoryImplTests {
    @Autowired
    private ServiceRepository serviceRepository;

    @Test
    void given_service_when_addservicerepository_then_returnequalsservice() {
        // given
        ServiceId idExpected = new ServiceId("42");
        Service serviceExpected = Service.create(idExpected, new ServiceName("TV"),
                new Price(new BigDecimal("42")));

        // when
        this.serviceRepository.add(serviceExpected);
        Service serviceActual = this.serviceRepository.serviceById(idExpected).get();

        // then
        assertEquals(serviceExpected, serviceActual);
        assertEquals(serviceExpected.getServiceId(), serviceActual.getServiceId());
    }
}
