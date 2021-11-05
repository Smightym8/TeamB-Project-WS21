package at.fhv.se.hotel.integration.infrastructure;

import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;
import at.fhv.se.hotel.domain.model.guest.Address;
import at.fhv.se.hotel.domain.model.guest.FullName;
import at.fhv.se.hotel.domain.model.guest.Guest;
import at.fhv.se.hotel.domain.model.guest.GuestId;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.infrastructure.HibernateBookingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class BookingRepositoryImplTests {
    @Autowired
    private HibernateBookingRepository bookingRepository;

    @Autowired
    private EntityManager em;

    @Test
    void given_booking_when_addbookingrepository_then_returnequalsbooking() {
        // given
        GuestId guestId = new GuestId("42");
        List<Booking> emptyBookingList = new ArrayList<>();
        Guest guest = Guest.create(guestId, new FullName("John", "Doe"),
                new Address("a", "a", "a", "a", "a"),
                LocalDate.now(), "phone", "mail", emptyBookingList);

        BookingId idExpected = new BookingId("42");
        List<RoomCategory> emptyCategoriesList = new ArrayList<>();
        List<Service> emptyServicesList = new ArrayList<>();
        Booking bookingExpected = Booking.create(LocalDate.now(), LocalDate.now().plusDays(10),
                idExpected, guest, emptyCategoriesList, emptyServicesList);

        // when
        this.bookingRepository.add(bookingExpected);
        this.em.flush();
        Booking bookingActual = this.bookingRepository.bookingById(idExpected).get();

        // then
        assertEquals(bookingExpected, bookingActual);
        assertEquals(bookingExpected.getBookingId(), bookingActual.getBookingId());
    }
}
