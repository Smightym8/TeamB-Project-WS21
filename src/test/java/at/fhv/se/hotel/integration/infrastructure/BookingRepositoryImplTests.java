package at.fhv.se.hotel.integration.infrastructure;

import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;
import at.fhv.se.hotel.domain.model.guest.Address;
import at.fhv.se.hotel.domain.model.guest.FullName;
import at.fhv.se.hotel.domain.model.guest.Gender;
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
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
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

    @Autowired
    private EntityManager em;

    @Test
    void given_booking_when_addbookingtorepository_then_returnequalsbooking() {
        // given
        Guest guestExpected = Guest.create(guestRepository.nextIdentity(),
                new FullName("Michael", "Spiegel"),
                Gender.MALE,
                new Address("Hochschulstraße",
                        "1", "Dornbirn",
                        "6850", "Austria"),
                LocalDate.of(1999, 3, 20),
                "+43 660 123 456 789",
                "michael.spiegel@students.fhv.at",
                0,
                Collections.emptyList()
        );

        RoomCategory categoryExpected = RoomCategory.create(roomCategoryRepository.nextIdentity(),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        List<Service> servicesExpected = Arrays.asList(
                Service.create(serviceRepository.nextIdentity(),
                        new ServiceName("TV"),
                        new Price(new BigDecimal("100"))),
                Service.create(serviceRepository.nextIdentity(),
                        new ServiceName("Breakfast"),
                        new Price(new BigDecimal("100")))
        );

        int amountOfAdultsExpected = 2;
        int amountOfChildrenExpected = 1;
        String additionalInfoExpected = "Nothing";
        BookingId idExpected = new BookingId("42");
        Booking bookingExpected = Booking.create(
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                idExpected,
                guestExpected,
                servicesExpected,
                amountOfAdultsExpected,
                amountOfChildrenExpected,
                ""
        );
        bookingExpected.addRoomCategory(categoryExpected, 1);

        // when
        servicesExpected.forEach(service -> this.serviceRepository.add(service));
        this.roomCategoryRepository.add(categoryExpected);
        this.bookingRepository.add(bookingExpected);
        em.flush();
        Booking bookingActual = this.bookingRepository.bookingById(idExpected).get();

        // then
        assertEquals(bookingExpected, bookingActual);
        assertEquals(bookingExpected.getBookingId(), bookingActual.getBookingId());
        assertEquals(bookingExpected.getGuest(), bookingActual.getGuest());
        assertEquals(bookingExpected.getCheckInDate(), bookingActual.getCheckInDate());
        assertEquals(bookingExpected.getCheckOutDate(), bookingActual.getCheckOutDate());
        assertEquals(bookingExpected.getRoomCategories().size(), bookingActual.getRoomCategories().size());
        assertEquals(bookingExpected.getServices().size(), bookingActual.getServices().size());
        assertEquals(bookingExpected.getAmountOfAdults(), bookingActual.getAmountOfAdults());
        assertEquals(bookingExpected.getAmountOfChildren(), bookingActual.getAmountOfChildren());
        assertEquals(additionalInfoExpected, bookingActual.getAdditionalInformation());
    }

    @Test
    void given_2bookings_when_addbookingstorepository_then_return2equalsbookings() {
        // given
        Guest guestExpected1 = Guest.create(guestRepository.nextIdentity(),
                new FullName("Michael", "Spiegel"),
                Gender.MALE,
                new Address("Hochschulstraße",
                        "1", "Dornbirn",
                        "6850", "Austria"),
                LocalDate.of(1999, 3, 20),
                "+43 660 123 456 789",
                "michael.spiegel@students.fhv.at",
                0,
                Collections.emptyList()
        );

        Guest guestExpected2 = Guest.create(guestRepository.nextIdentity(),
                new FullName("Ali", "Cinar"),
                Gender.MALE,
                new Address("Hochschulstraße",
                        "1", "Dornbirn",
                        "6850", "Austria"),
                LocalDate.of(1997, 8, 27),
                "+43 676 123 456 789",
                "ali.cinar@students.fhv.at",
                0,
                Collections.emptyList()
        );

        RoomCategory categoryExpected1 = RoomCategory.create(roomCategoryRepository.nextIdentity(),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        RoomCategory categoryExpected2 = RoomCategory.create(roomCategoryRepository.nextIdentity(),
                new RoomCategoryName("Double Room"),
                new Description("This is a double room")
        );

        List<Service> servicesExpected1 = Arrays.asList(
                Service.create(serviceRepository.nextIdentity(),
                        new ServiceName("TV"),
                        new Price(new BigDecimal("100"))),
                Service.create(serviceRepository.nextIdentity(),
                        new ServiceName("Breakfast"),
                        new Price(new BigDecimal("100")))
        );

        List<Service> servicesExpected2 = Arrays.asList(
                Service.create(serviceRepository.nextIdentity(),
                        new ServiceName("TV"),
                        new Price(new BigDecimal("100"))),
                Service.create(serviceRepository.nextIdentity(),
                        new ServiceName("Breakfast"),
                        new Price(new BigDecimal("100"))),
                Service.create(serviceRepository.nextIdentity(),
                        new ServiceName("Internet"),
                        new Price(new BigDecimal("40")))
        );

        int amountOfAdultsExpected = 2;
        int amountOfChildrenExpected = 1;
        String additionalInfoExpected = "Vegan";

        BookingId idExpected1 = new BookingId("42");
        Booking bookingExpected1 = Booking.create(
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                idExpected1,
                guestExpected1,
                servicesExpected1,
                amountOfAdultsExpected,
                amountOfChildrenExpected,
                additionalInfoExpected
        );

        BookingId idExpected2 = new BookingId("1337");
        Booking bookingExpected2 = Booking.create(
                LocalDate.now().plusDays(3),
                LocalDate.now().plusDays(13),
                idExpected2,
                guestExpected2,
                servicesExpected2,
                amountOfAdultsExpected,
                amountOfChildrenExpected,
                additionalInfoExpected
        );

        bookingExpected1.addRoomCategory(categoryExpected1, 1);
        bookingExpected2.addRoomCategory(categoryExpected2, 1);

        List<Booking> bookingsExpected = Arrays.asList(bookingExpected1, bookingExpected2);

        // when
        servicesExpected1.forEach(service -> this.serviceRepository.add(service));
        this.roomCategoryRepository.add(categoryExpected1);
        this.bookingRepository.add(bookingExpected1);

        servicesExpected2.forEach(service -> this.serviceRepository.add(service));
        this.roomCategoryRepository.add(categoryExpected2);
        this.bookingRepository.add(bookingExpected2);

        em.flush();
        List<Booking> bookingsActual = this.bookingRepository.findAllBookings();

        // then
        assertEquals(bookingsExpected.size(), bookingsActual.size());
        for (int i = 0; i < 2; i++) {
            assertEquals(bookingsExpected.get(i).getBookingId(), bookingsActual.get(i).getBookingId());
            assertEquals(bookingsExpected.get(i).getGuest(), bookingsActual.get(i).getGuest());
            assertEquals(bookingsExpected.get(i).getCheckInDate(), bookingsActual.get(i).getCheckInDate());
            assertEquals(bookingsExpected.get(i).getCheckOutDate(), bookingsActual.get(i).getCheckOutDate());
            assertEquals(bookingsExpected.get(i).getRoomCategories().size(), bookingsActual.get(i).getRoomCategories().size());
            assertEquals(bookingsExpected.get(i).getServices().size(), bookingsActual.get(i).getServices().size());
            assertEquals(bookingsExpected.get(i).getAmountOfAdults(), bookingsActual.get(i).getAmountOfAdults());
            assertEquals(bookingsExpected.get(i).getAmountOfChildren(), bookingsActual.get(i).getAmountOfChildren());
            assertEquals(bookingsExpected.get(i).getAdditionalInformation(), bookingsActual.get(i).getAdditionalInformation());
        }
    }
}
