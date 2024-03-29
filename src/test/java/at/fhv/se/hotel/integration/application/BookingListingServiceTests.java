package at.fhv.se.hotel.integration.application;

import at.fhv.se.hotel.application.api.BookingListingService;
import at.fhv.se.hotel.application.dto.BookingListingDTO;
import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;
import at.fhv.se.hotel.domain.model.guest.*;
import at.fhv.se.hotel.domain.model.roomcategory.Description;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryName;
import at.fhv.se.hotel.domain.model.service.Price;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.service.ServiceId;
import at.fhv.se.hotel.domain.model.service.ServiceName;
import at.fhv.se.hotel.domain.repository.BookingRepository;
import at.fhv.se.hotel.domain.repository.GuestRepository;
import at.fhv.se.hotel.domain.repository.RoomCategoryRepository;
import at.fhv.se.hotel.domain.repository.ServiceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class BookingListingServiceTests {
    @Autowired
    BookingListingService bookingListingService;

    @MockBean
    BookingRepository bookingRepository;

    @Test
    void given_3bookingsinrepository_when_fetchingall_then_returnequalsbookings() {
        // given
        Mockito.when(bookingRepository.nextIdentity()).thenReturn(
                new BookingId(UUID.randomUUID().toString().toUpperCase())
        );

        Guest guestExpected1 = Guest.create(
                new GuestId("1"),
                new FullName("John", "Doe"),
                Gender.MALE,
                new Address("Hochschulstraße",
                        "1", "Dornbirn",
                        "6850", "Austria"),
                LocalDate.of(1980, 5, 20),
                "+43 660 123 456 789",
                "john.doe@developer.tdd.at",
                0,
                Collections.emptyList()
        );

        Guest guestExpected2 = Guest.create(
                new GuestId("2"),
                new FullName("Michael", "Spiegel"),
                Gender.MALE,
                new Address("Hochschulstraße",
                        "1", "Dornbirn",
                        "6850", "Austria"),
                LocalDate.of(1999, 3, 20),
                "+43 660 123 456 789",
                "michael.spiegel@developer.tdd.at",
                0,
                Collections.emptyList()
        );

        Guest guestExpected3 = Guest.create(new GuestId("3"),
                new FullName("Ali", "Cinar"),
                Gender.MALE,
                new Address("Hochschulstraße",
                        "1", "Dornbirn",
                        "6850", "Austria"),
                LocalDate.of(1997, 8, 27),
                "+43 676 123 456 789",
                "ali.cinar@developer.tdd.at",
                0,
                Collections.emptyList()
        );

        Service serviceExpected1 = Service.create(
                new ServiceId("1"),
                new ServiceName("Breakfast"),
                new Price(new BigDecimal("100"))
        );

        Service serviceExpected2 = Service.create(
                new ServiceId("2"),
                new ServiceName("TV"),
                new Price(new BigDecimal("100"))
        );

        RoomCategory categoryExpected1 = RoomCategory.create(new RoomCategoryId("1"),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        RoomCategory categoryExpected2 = RoomCategory.create(new RoomCategoryId("2"),
                new RoomCategoryName("Double Room"),
                new Description("This is a double room")
        );

        Booking bookingExpected1 = Booking.create(
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                bookingRepository.nextIdentity(),
                guestExpected1,
                List.of(serviceExpected1),
                2,
                0,
                "Extra pillow",
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001"
        );
        bookingExpected1.addRoomCategory(categoryExpected1, 1);

        Booking bookingExpected2 = Booking.create(
                LocalDate.now(),
                LocalDate.now().plusDays(20),
                bookingRepository.nextIdentity(),
                guestExpected2,
                List.of(serviceExpected2),
                2,
                1,
                "Vegan",
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "002"
        );
        bookingExpected2.addRoomCategory(categoryExpected1, 1);
        bookingExpected2.addRoomCategory(categoryExpected2, 1);

        Booking bookingExpected3 = Booking.create(
                LocalDate.now(),
                LocalDate.now().plusDays(3),
                bookingRepository.nextIdentity(),
                guestExpected3,
                List.of(serviceExpected1),
                2,
                4,
                "Vegan",
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "003"
        );
        bookingExpected3.addRoomCategory(categoryExpected1, 2);
        bookingExpected3.addRoomCategory(categoryExpected2, 2);

        List<Booking> bookingsExpected = List.of(
                bookingExpected1,
                bookingExpected2,
                bookingExpected3
        );

        Mockito.when(bookingRepository.findAllBookings()).thenReturn(bookingsExpected);

        // when
        List<BookingListingDTO> bookingsActual = bookingListingService.allBookings();

        // then
        assertEquals(bookingsExpected.size(), bookingsActual.size());
        for(int i = 0; i < bookingsExpected.size(); i++) {
            assertEquals(bookingsExpected.get(i).getBookingId().id(), bookingsActual.get(i).id());
            assertEquals(bookingsExpected.get(i).getBookingNumber(), bookingsActual.get(i).bookingNumber());
            assertEquals(bookingsExpected.get(i).getCheckInDate(), bookingsActual.get(i).checkInDate());
            assertEquals(bookingsExpected.get(i).getCheckOutDate(), bookingsActual.get(i).checkOutDate());
            assertEquals(bookingsExpected.get(i).isActive(), bookingsActual.get(i).isActive());
            assertEquals(bookingsExpected.get(i).getGuest().getAddress().city(), bookingsActual.get(i).city());
            assertEquals(bookingsExpected.get(i).getGuest().getAddress().zipCode(), bookingsActual.get(i).zipCode());
            assertEquals(bookingsExpected.get(i).getGuest().getAddress().streetNumber(), bookingsActual.get(i).streetNumber());
            assertEquals(bookingsExpected.get(i).getGuest().getAddress().streetName(), bookingsActual.get(i).streetName());
            assertEquals(bookingsExpected.get(i).getGuest().getName().firstName(), bookingsActual.get(i).guestFirstName());
            assertEquals(bookingsExpected.get(i).getGuest().getName().lastName(), bookingsActual.get(i).guestLastName());
        }
    }
}
