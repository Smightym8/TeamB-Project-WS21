package at.fhv.se.hotel;

import at.fhv.se.hotel.domain.model.guest.Address;
import at.fhv.se.hotel.domain.model.guest.FullName;
import at.fhv.se.hotel.domain.model.guest.Guest;
import at.fhv.se.hotel.domain.model.roomcategory.Description;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryName;
import at.fhv.se.hotel.domain.model.service.Price;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.service.ServiceId;
import at.fhv.se.hotel.domain.model.service.ServiceName;
import at.fhv.se.hotel.domain.repository.GuestRepository;
import at.fhv.se.hotel.domain.repository.RoomCategoryRepository;
import at.fhv.se.hotel.domain.repository.ServiceRepository;
import at.fhv.se.hotel.infrastructure.HibernateServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

@Component
@Transactional
public class TestData implements ApplicationRunner {
    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    RoomCategoryRepository roomCategoryRepository;

    @Autowired
    GuestRepository guestRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Insert fake services
        Service tvService = Service.create(serviceRepository.nextIdentity(), new ServiceName("TV"), new Price(new BigDecimal("100")));
        Service breakfastService = Service.create(serviceRepository.nextIdentity(), new ServiceName("Breakfast"), new Price(new BigDecimal("100")));

        this.serviceRepository.add(tvService);
        this.serviceRepository.add(breakfastService);

        // Insert fake categories
        RoomCategory singleRoom = RoomCategory.create(roomCategoryRepository.nextIdentity(),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room"));

        RoomCategory doubleRoom = RoomCategory.create(roomCategoryRepository.nextIdentity(),
                new RoomCategoryName("Double Room"),
                new Description("This is a double room"));

        this.roomCategoryRepository.add(singleRoom);
        this.roomCategoryRepository.add(doubleRoom);

        // Insert fake guests
        Guest michael = Guest.create(guestRepository.nextIdentity(),
                new FullName("Michael", "Spiegel"),
                new Address("Hochschulstraße",
                        "1", "Dornbirn",
                        "6850", "Austria"),
                LocalDate.of(1999, 3, 20),
                "+43 660 123 456 789",
                "michael.spiegel@students.fhv.at",
                Collections.emptyList());
        this.guestRepository.add(michael);
    }
}
