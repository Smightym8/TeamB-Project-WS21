package at.fhv.se.hotel.integration.application;

import at.fhv.se.hotel.application.api.BookingCreationService;
import at.fhv.se.hotel.application.api.exception.GuestNotFoundException;
import at.fhv.se.hotel.application.api.exception.RoomCategoryNotFoundException;
import at.fhv.se.hotel.application.api.exception.ServiceNotFoundException;
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
import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.repository.BookingRepository;
import at.fhv.se.hotel.domain.repository.GuestRepository;
import at.fhv.se.hotel.domain.repository.RoomCategoryRepository;
import at.fhv.se.hotel.domain.repository.ServiceRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class BookingCreationServiceTests {
    @Autowired
    BookingCreationService bookingCreationService;

    @MockBean
    BookingRepository bookingRepository;

    @MockBean
    GuestRepository guestRepository;

    @MockBean
    ServiceRepository serviceRepository;

    @MockBean
    RoomCategoryRepository roomCategoryRepository;

    @Test
    public void given_bookingFormData_when_book_then_bookingWithMatchingDetailsCreated() throws RoomCategoryNotFoundException, ServiceNotFoundException, GuestNotFoundException {
        // given
        BookingId bookingIdExpected = new BookingId("1");
        GuestId guestIdExpected = new GuestId("1");
        ServiceId serviceIdExpected = new ServiceId("1");
        RoomCategoryId roomCategoryIdExpected = new RoomCategoryId("1");

        List<String> roomCategoryIdsExpected = List.of(roomCategoryIdExpected.id());
        List<Integer> amounts = List.of(1);
        List<String> serviceIdsExpected = List.of(serviceIdExpected.id());
        LocalDate checkInDateExpected = LocalDate.of(2022, 1, 10);
        LocalDate checkOutDateExpected = LocalDate.of(2022, 1, 20);
        int amountOfAdultsExpected = 2;
        int amountOfChildrenExpected = 2;

        Guest guestExpected = Guest.create(
                guestIdExpected,
                new FullName("Maria", "Doe"),
                Gender.FEMALE,
                new Address("Street", "1", "Dornbirn", "6850", "Austria"),
                LocalDate.of(1982, 4, 3),
                "+43 123456789",
                "maria.doe@tdd.at",
                Collections.emptyList()
        );

        Service serviceExpected = Service.create(
                serviceIdExpected,
                new ServiceName("Breakfast"),
                new Price(new BigDecimal("100"))
        );

        RoomCategory roomCategoryExpected = RoomCategory.create(
                roomCategoryIdExpected,
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        Mockito.when(bookingRepository.nextIdentity()).thenReturn(bookingIdExpected);
        Mockito.when(guestRepository.guestById(guestIdExpected)).thenReturn(Optional.of(guestExpected));
        Mockito.when(serviceRepository.serviceById(serviceIdExpected)).thenReturn(Optional.of(serviceExpected));
        Mockito.when(roomCategoryRepository.roomCategoryById(roomCategoryIdExpected)).thenReturn(Optional.of(roomCategoryExpected));

        // when
        String bookingIdActual = bookingCreationService.book(
                guestIdExpected.id(),
                roomCategoryIdsExpected,
                amounts,
                serviceIdsExpected,
                checkInDateExpected,
                checkOutDateExpected,
                amountOfAdultsExpected,
                amountOfChildrenExpected,
                "None"
        );

        ArgumentCaptor<Booking> bookingCaptor = ArgumentCaptor.forClass(Booking.class);
        Mockito.verify(bookingRepository).add(bookingCaptor.capture());
        Booking bookingActual = bookingCaptor.getValue();

        // then
        assertEquals(bookingIdExpected.id(), bookingIdActual);
        assertEquals(guestExpected, bookingActual.getGuest());
        assertEquals(serviceIdsExpected.size(), bookingActual.getServices().size());
        assertEquals(roomCategoryIdsExpected.size(), bookingActual.getRoomCategories().size());
        assertEquals(checkInDateExpected, bookingActual.getCheckInDate());
        assertEquals(checkOutDateExpected, bookingActual.getCheckOutDate());
        assertTrue(bookingActual.isActive());
    }

    @Test
    public void given_missingGuest_when_createBooking_then_GuestNotFoundExceptionIsThrown() {
        // given
        BookingId bookingIdExpected = new BookingId("1");
        GuestId guestIdExpected = new GuestId("1");
        ServiceId serviceIdExpected = new ServiceId("1");
        RoomCategoryId roomCategoryIdExpected = new RoomCategoryId("1");

        List<String> roomCategoryIdsExpected = List.of(roomCategoryIdExpected.id());
        List<Integer> amounts = List.of(1);
        List<String> serviceIdsExpected = List.of(serviceIdExpected.id());
        LocalDate checkInDateExpected = LocalDate.of(2022, 1, 10);
        LocalDate checkOutDateExpected = LocalDate.of(2022, 1, 20);
        int amountOfAdultsExpected = 2;
        int amountOfChildrenExpected = 2;

        Mockito.when(bookingRepository.nextIdentity()).thenReturn(bookingIdExpected);
        Mockito.when(guestRepository.guestById(guestIdExpected)).thenReturn(Optional.empty());

        // when ... then
        Exception exception = assertThrows(GuestNotFoundException.class, () -> {
            bookingCreationService.book(
                    guestIdExpected.id(),
                    roomCategoryIdsExpected,
                    amounts,
                    serviceIdsExpected,
                    checkInDateExpected,
                    checkOutDateExpected,
                    amountOfAdultsExpected,
                    amountOfChildrenExpected,
                    "None"
            );
        });

        String expectedMessage = "Guest with id " + guestIdExpected.id() + " not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void given_missingService_when_createBooking_then_ServiceNotFoundExceptionIsThrown() {
        // given
        BookingId bookingIdExpected = new BookingId("1");
        GuestId guestIdExpected = new GuestId("1");
        ServiceId serviceIdExpected = new ServiceId("1");
        RoomCategoryId roomCategoryIdExpected = new RoomCategoryId("1");

        List<String> roomCategoryIdsExpected = List.of(roomCategoryIdExpected.id());
        List<Integer> amounts = List.of(1);
        List<String> serviceIdsExpected = List.of(serviceIdExpected.id());
        LocalDate checkInDateExpected = LocalDate.of(2022, 1, 10);
        LocalDate checkOutDateExpected = LocalDate.of(2022, 1, 20);
        int amountOfAdultsExpected = 2;
        int amountOfChildrenExpected = 2;

        Guest guestExpected = Guest.create(
                guestIdExpected,
                new FullName("Maria", "Doe"),
                Gender.FEMALE,
                new Address("Street", "1", "Dornbirn", "6850", "Austria"),
                LocalDate.of(1982, 4, 3),
                "+43 123456789",
                "maria.doe@tdd.at",
                Collections.emptyList()
        );

        Mockito.when(bookingRepository.nextIdentity()).thenReturn(bookingIdExpected);
        Mockito.when(guestRepository.guestById(guestIdExpected)).thenReturn(Optional.of(guestExpected));
        Mockito.when(serviceRepository.serviceById(serviceIdExpected)).thenReturn(Optional.empty());

        // when ... then
        Exception exception = assertThrows(ServiceNotFoundException.class, () -> {
            bookingCreationService.book(
                    guestIdExpected.id(),
                    roomCategoryIdsExpected,
                    amounts,
                    serviceIdsExpected,
                    checkInDateExpected,
                    checkOutDateExpected,
                    amountOfAdultsExpected,
                    amountOfChildrenExpected,
                    "None"
            );
        });

        String expectedMessage = "Service with id " + serviceIdExpected.id() + " not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void given_missingRoomCategory_when_createBooking_then_RoomCategoryNotFoundExceptionIsThrown() {
        // given
        BookingId bookingIdExpected = new BookingId("1");
        GuestId guestIdExpected = new GuestId("1");
        ServiceId serviceIdExpected = new ServiceId("1");
        RoomCategoryId roomCategoryIdExpected = new RoomCategoryId("1");

        List<String> roomCategoryIdsExpected = List.of(roomCategoryIdExpected.id());
        List<Integer> amounts = List.of(1);
        List<String> serviceIdsExpected = List.of(serviceIdExpected.id());
        LocalDate checkInDateExpected = LocalDate.of(2022, 1, 10);
        LocalDate checkOutDateExpected = LocalDate.of(2022, 1, 20);
        int amountOfAdultsExpected = 2;
        int amountOfChildrenExpected = 2;

        Guest guestExpected = Guest.create(
                guestIdExpected,
                new FullName("Maria", "Doe"),
                Gender.FEMALE,
                new Address("Street", "1", "Dornbirn", "6850", "Austria"),
                LocalDate.of(1982, 4, 3),
                "+43 123456789",
                "maria.doe@tdd.at",
                Collections.emptyList()
        );

        Service serviceExpected = Service.create(
                serviceIdExpected,
                new ServiceName("Breakfast"),
                new Price(new BigDecimal("100"))
        );

        Mockito.when(bookingRepository.nextIdentity()).thenReturn(bookingIdExpected);
        Mockito.when(guestRepository.guestById(guestIdExpected)).thenReturn(Optional.of(guestExpected));
        Mockito.when(serviceRepository.serviceById(serviceIdExpected)).thenReturn(Optional.of(serviceExpected));
        Mockito.when(roomCategoryRepository.roomCategoryById(roomCategoryIdExpected)).thenReturn(Optional.empty());

        // when ... then
        Exception exception = assertThrows(RoomCategoryNotFoundException.class, () -> {
            bookingCreationService.book(
                    guestIdExpected.id(),
                    roomCategoryIdsExpected,
                    amounts,
                    serviceIdsExpected,
                    checkInDateExpected,
                    checkOutDateExpected,
                    amountOfAdultsExpected,
                    amountOfChildrenExpected,
                    "None"
            );
        });

        String expectedMessage = "RoomCategory with id " + roomCategoryIdExpected.id() + " not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
