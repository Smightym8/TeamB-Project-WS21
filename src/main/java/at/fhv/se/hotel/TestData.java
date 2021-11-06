package at.fhv.se.hotel;

import at.fhv.se.hotel.domain.model.service.Price;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.service.ServiceId;
import at.fhv.se.hotel.domain.model.service.ServiceName;
import at.fhv.se.hotel.domain.repository.ServiceRepository;
import at.fhv.se.hotel.infrastructure.HibernateServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;

@Component
@Transactional
public class TestData implements ApplicationRunner {
    @Autowired
    HibernateServiceRepository serviceRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Service tvService = Service.create(serviceRepository.nextIdentity(), new ServiceName("TV"), new Price(new BigDecimal("100")));
        Service breakfastService = Service.create(serviceRepository.nextIdentity(), new ServiceName("Breakfast"), new Price(new BigDecimal("100")));

        this.serviceRepository.add(tvService);
        this.serviceRepository.add(breakfastService);
    }
}
