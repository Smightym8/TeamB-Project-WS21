package at.fhv.se.hotel.integration.application;

import at.fhv.se.hotel.application.api.BookingSummaryService;
import at.fhv.se.hotel.application.api.exception.BookingNotFoundException;
import at.fhv.se.hotel.application.api.exception.GuestNotFoundException;
import at.fhv.se.hotel.application.api.exception.RoomCategoryNotFoundException;
import at.fhv.se.hotel.application.api.exception.ServiceNotFoundException;
import at.fhv.se.hotel.application.dto.BookingDetailsDTO;
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
import org.junit.jupiter.api.Test;
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
public class BookingSummaryServiceTests {
    @Autowired
    BookingSummaryService bookingSummaryService;

    @MockBean
    GuestRepository guestRepository;

    @MockBean
    RoomCategoryRepository roomCategoryRepository;

    @MockBean
    ServiceRepository serviceRepository;

    @MockBean
    BookingRepository bookingRepository;

    @Test
    public void given_bookingDetails_when_createSummary_then_returnBookingDTOWithEqualDetails() throws RoomCategoryNotFoundException, ServiceNotFoundException, GuestNotFoundException {
        // given
        GuestId guestIdExpected = new GuestId("1");
        RoomCategoryId roomCategoryIdExpected = new RoomCategoryId("1");
        List<Integer> amounts = List.of(1);
        ServiceId serviceIdExpected = new ServiceId("1");
        List<String> roomCategoryIdsExpected = List.of(roomCategoryIdExpected.id());
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
                0.0,
                Collections.emptyList()
        );

        RoomCategory roomCategoryExpected = RoomCategory.create(
                roomCategoryIdExpected,
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        Service serviceExpected = Service.create(
                serviceIdExpected,
                new ServiceName("Breakfast"),
                new Price(new BigDecimal("100"))
        );

        Mockito.when(guestRepository.guestById(guestIdExpected)).thenReturn(Optional.of(guestExpected));
        Mockito.when(roomCategoryRepository.roomCategoryById(roomCategoryIdExpected)).thenReturn(Optional.of(roomCategoryExpected));
        Mockito.when(serviceRepository.serviceById(serviceIdExpected)).thenReturn(Optional.of(serviceExpected));

        // when
        BookingDetailsDTO bookingDetailsDTO = bookingSummaryService.createSummary(
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

        // then
        assertEquals(guestIdExpected.id(), bookingDetailsDTO.guestId());
        assertEquals(guestExpected.getName().firstName(), bookingDetailsDTO.guestFirstName());
        assertEquals(guestExpected.getName().lastName(), bookingDetailsDTO.guestLastName());
        assertEquals(roomCategoryIdsExpected.size(), bookingDetailsDTO.categoriesWithAmounts().size());
        assertEquals(serviceIdsExpected.size(), bookingDetailsDTO.services().size());
        assertEquals(checkInDateExpected, bookingDetailsDTO.checkInDate());
        assertEquals(checkOutDateExpected, bookingDetailsDTO.checkOutDate());
        assertEquals(amountOfAdultsExpected, bookingDetailsDTO.amountOfAdults());
        assertEquals(amountOfChildrenExpected, bookingDetailsDTO.amountOfChildren());
    }

    @Test
    public void given_missingGuest_when_createSummary_then_GuestNotFoundExceptionIsThrown() {
        // given
        GuestId guestIdExpected = new GuestId("1");
        RoomCategoryId roomCategoryIdExpected = new RoomCategoryId("1");
        List<Integer> amounts = List.of(1);
        ServiceId serviceIdExpected = new ServiceId("1");
        List<String> roomCategoryIdsExpected = List.of(roomCategoryIdExpected.id());
        List<String> serviceIdsExpected = List.of(serviceIdExpected.id());
        LocalDate checkInDateExpected = LocalDate.of(2022, 1, 10);
        LocalDate checkOutDateExpected = LocalDate.of(2022, 1, 20);
        int amountOfAdultsExpected = 2;
        int amountOfChildrenExpected = 2;

        Mockito.when(guestRepository.guestById(guestIdExpected)).thenReturn(Optional.empty());

        // when ... then
        Exception exception = assertThrows(GuestNotFoundException.class, () -> {
            bookingSummaryService.createSummary(
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
    public void given_missingRoomCategory_when_createSummary_then_RoomCategoryNotFoundExceptionIsThrown() {
        // given
        GuestId guestIdExpected = new GuestId("1");
        RoomCategoryId roomCategoryIdExpected = new RoomCategoryId("1");
        List<Integer> amounts = List.of(1);
        ServiceId serviceIdExpected = new ServiceId("1");
        List<String> roomCategoryIdsExpected = List.of(roomCategoryIdExpected.id());
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
                0.0,
                Collections.emptyList()
        );

        Mockito.when(guestRepository.guestById(guestIdExpected)).thenReturn(Optional.of(guestExpected));
        Mockito.when(roomCategoryRepository.roomCategoryById(roomCategoryIdExpected)).thenReturn(Optional.empty());

        // when ... then
        Exception exception = assertThrows(RoomCategoryNotFoundException.class, () -> {
            bookingSummaryService.createSummary(
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

        String expectedMessage = "Category with id " + roomCategoryIdExpected.id() + " not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void given_missingService_when_createSummary_then_ServiceNotFoundExceptionIsThrown() {
        // given
        GuestId guestIdExpected = new GuestId("1");
        RoomCategoryId roomCategoryIdExpected = new RoomCategoryId("1");
        List<Integer> amounts = List.of(1);
        ServiceId serviceIdExpected = new ServiceId("1");
        List<String> roomCategoryIdsExpected = List.of(roomCategoryIdExpected.id());
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
                0.0,
                Collections.emptyList()
        );

        RoomCategory roomCategoryExpected = RoomCategory.create(
                roomCategoryIdExpected,
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        Mockito.when(guestRepository.guestById(guestIdExpected)).thenReturn(Optional.of(guestExpected));
        Mockito.when(roomCategoryRepository.roomCategoryById(roomCategoryIdExpected)).thenReturn(Optional.of(roomCategoryExpected));
        Mockito.when(serviceRepository.serviceById(serviceIdExpected)).thenReturn(Optional.empty());

        // when ... then
        Exception exception = assertThrows(ServiceNotFoundException.class, () -> {
            bookingSummaryService.createSummary(
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
    public void given_bookingId_when_getDetailsById_then_returnMatchingBookingDetails() throws BookingNotFoundException {
        // given
        LocalDate checkInDateExpected = LocalDate.of(2022, 1, 10);
        LocalDate checkOutDateExpected = LocalDate.of(2022, 1, 20);
        BookingId bookingIdExpected = new BookingId("1");
        GuestId guestIdExpected = new GuestId("1");
        RoomCategoryId roomCategoryIdExpected = new RoomCategoryId("1");
        ServiceId serviceIdExpected = new ServiceId("1");
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
                0.0,
                Collections.emptyList()
        );

        RoomCategory roomCategoryExpected = RoomCategory.create(
                roomCategoryIdExpected,
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        Service serviceExpected = Service.create(
                serviceIdExpected,
                new ServiceName("Breakfast"),
                new Price(new BigDecimal("100"))
        );

        Booking bookingExpected = Booking.create(
                checkInDateExpected,
                checkOutDateExpected,
                bookingIdExpected,
                guestExpected,
                List.of(serviceExpected),
                amountOfAdultsExpected,
                amountOfChildrenExpected,
                "None"
        );
        bookingExpected.addRoomCategory(roomCategoryExpected, 1);

        Mockito.when(bookingRepository.bookingById(bookingIdExpected)).thenReturn(Optional.of(bookingExpected));

        // when
        BookingDetailsDTO bookingDetailsDTO = bookingSummaryService.detailsByBookingId(bookingIdExpected.id());

        // then
        assertEquals(bookingIdExpected.id(), bookingDetailsDTO.bookingId());
        assertEquals(guestExpected.getName().firstName(), bookingDetailsDTO.guestFirstName());
        assertEquals(guestExpected.getName().lastName(), bookingDetailsDTO.guestLastName());
        assertEquals(bookingExpected.getRoomCategories().size(), bookingDetailsDTO.categoriesWithAmounts().size());
        assertEquals(bookingExpected.getServices().size(), bookingDetailsDTO.services().size());
        assertEquals(checkInDateExpected, bookingDetailsDTO.checkInDate());
        assertEquals(checkOutDateExpected, bookingDetailsDTO.checkOutDate());
        assertEquals(amountOfAdultsExpected, bookingDetailsDTO.amountOfAdults());
        assertEquals(amountOfChildrenExpected, bookingDetailsDTO.amountOfChildren());
    }

    @Test
    public void given_missingBooking_when_getDetailsById_then_BookingNotFoundExceptionIsThrown() {
        // given
        BookingId bookingIdExpected = new BookingId("1");
        Mockito.when(bookingRepository.bookingById(bookingIdExpected)).thenReturn(Optional.empty());

        // when ... then
        Exception exception = assertThrows(BookingNotFoundException.class, () -> {
            bookingSummaryService.detailsByBookingId(bookingIdExpected.id());
        });

        String expectedMessage = "Booking with id " + bookingIdExpected.id() + " not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
