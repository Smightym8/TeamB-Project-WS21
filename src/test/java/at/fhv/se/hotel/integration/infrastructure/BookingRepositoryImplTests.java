package at.fhv.se.hotel.integration.infrastructure;

import at.fhv.se.hotel.domain.model.booking.BookingId;
import at.fhv.se.hotel.domain.model.guest.Address;
import at.fhv.se.hotel.domain.model.guest.FullName;
import at.fhv.se.hotel.domain.model.guest.Guest;
import at.fhv.se.hotel.domain.model.roomcategory.Description;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryName;
import at.fhv.se.hotel.domain.model.service.Price;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.service.ServiceName;
import at.fhv.se.hotel.domain.repository.BookingRepository;
import at.fhv.se.hotel.domain.repository.GuestRepository;
import at.fhv.se.hotel.domain.repository.RoomCategoryRepository;
import at.fhv.se.hotel.domain.repository.ServiceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class BookingRepositoryImplTests {
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    GuestRepository guestRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    RoomCategoryRepository roomCategoryRepository;

    @Test
    void given_booking_when_addbookingtorepository_then_returnequalsbooking() {
        // given
        Guest guestExpected = Guest.create(guestRepository.nextIdentity(),
                new FullName("Michael", "Spiegel"),
                new Address("Hochschulstraße",
                        "1", "Dornbirn",
                        "6850", "Austria"),
                LocalDate.of(1999, 3, 20),
                "+43 660 123 456 789",
                "michael.spiegel@students.fhv.at",
                Collections.emptyList());

        List<RoomCategory> categoriesExpected = Arrays.asList(
                RoomCategory.create(roomCategoryRepository.nextIdentity(),
                        new RoomCategoryName("Single Room"),
                        new Description("This is a single room")),
                RoomCategory.create(roomCategoryRepository.nextIdentity(),
                        new RoomCategoryName("Double Room"),
                        new Description("This is a double room"))
        );

        List<Service> servicesExpected = Arrays.asList(
                Service.create(serviceRepository.nextIdentity(),
                        new ServiceName("TV"),
                        new Price(new BigDecimal("100"))),
                Service.create(serviceRepository.nextIdentity(),
                        new ServiceName("Breakfast"),
                        new Price(new BigDecimal("100")))
        );

        BookingId idExpcted = new BookingId("42");
        Booking bookingExpected = Booking.create(
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                idExpcted,
                guestExpected,
                categoriesExpected,
                servicesExpected
        );

        // when
        this.bookingRepository.add(bookingExpected);
        Booking bookingActual = this.bookingRepository.bookingById(idExpcted).get();

        // then
        assertEquals(bookingExpected, bookingActual);
        assertEquals(bookingExpected.getBookingId(), bookingActual.getBookingId());
        assertEquals(bookingExpected.getGuest(), bookingActual.getGuest());
        assertEquals(bookingExpected.getCheckInDate(), bookingActual.getCheckInDate());
        assertEquals(bookingExpected.getCheckOutDate(), bookingActual.getCheckOutDate());
        assertEquals(bookingExpected.getRoomCategories().size(), bookingActual.getRoomCategories().size());
        assertEquals(bookingExpected.getServices().size(), bookingActual.getServices().size());

        for (RoomCategory rc : bookingActual.getRoomCategories()) {
            assertTrue(categoriesExpected.contains(rc));
        }

        for(Service s : bookingActual.getServices()) {
            assertTrue(servicesExpected.contains(s));
        }
    }
}
