package at.fhv.se.hotel.integration.application;

import at.fhv.se.hotel.application.api.CheckInService;
import at.fhv.se.hotel.application.api.exception.BookingNotFoundException;
import at.fhv.se.hotel.application.api.exception.RoomNotFoundException;
import at.fhv.se.hotel.application.dto.RoomDTO;
import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;
import at.fhv.se.hotel.domain.model.booking.BookingWithRoomCategory;
import at.fhv.se.hotel.domain.model.booking.BookingWithRoomCategoryId;
import at.fhv.se.hotel.domain.model.guest.*;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomStatus;
import at.fhv.se.hotel.domain.model.roomcategory.Description;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryId;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategoryName;
import at.fhv.se.hotel.domain.model.service.Price;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.service.ServiceId;
import at.fhv.se.hotel.domain.model.service.ServiceName;
import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.model.stay.StayId;
import at.fhv.se.hotel.domain.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class CheckInServiceTest {
    @Autowired
    private CheckInService checkInService;

    @MockBean
    BookingRepository bookingRepository;

    @MockBean
    RoomRepository roomRepository;

    @MockBean
    StayRepository stayRepository;

    @Test
    void given_booking_when_assignRooms_then_returnexpectedrooms() throws BookingNotFoundException {
        //given
        Guest guestExpected = Guest.create(new GuestId("1"),
                new FullName("Michael", "Spiegel"),
                Gender.MALE,
                new Address("Hochschulstraße",
                        "1", "Dornbirn",
                        "6850", "Austria"),
                LocalDate.of(1999, 3, 20),
                "+43 660 123 456 789",
                "michael.spiegel@students.fhv.at",
                0.0,
                Collections.emptyList()
        );

        List<Service> servicesExpected = List.of(
                Service.create(new ServiceId("1"),
                        new ServiceName("TV"),
                        new Price(new BigDecimal("100"))),
                Service.create(new ServiceId("2"),
                        new ServiceName("Breakfast"),
                        new Price(new BigDecimal("100")))
        );

        BookingId idExpected = new BookingId("1");
        Booking bookingExpected = Booking.create(
                LocalDate.of(2021, 8, 1),
                LocalDate.of(2021, 8, 10),
                idExpected,
                guestExpected,
                servicesExpected,
                2,
                1,
                "Nothing"
        );

        RoomCategory roomCategoryExpected = RoomCategory.create(new RoomCategoryId("1"),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room"));

        List<String> roomNamesExpected = Arrays.asList("101", "102", "103");

        RoomStatus roomStatusExpected = RoomStatus.FREE;

        List<Room> roomsExpected = roomNamesExpected.stream()
                .map(name -> Room.create(name, roomStatusExpected, roomCategoryExpected))
                .collect(Collectors.toList());

        bookingExpected.addRoomCategory(roomCategoryExpected, 3);

        Mockito.when(bookingRepository.bookingById(idExpected)).thenReturn(Optional.of(bookingExpected));
        Mockito.when(roomRepository.roomsByCategoryAndStatus(roomCategoryExpected.getRoomCategoryId(), roomStatusExpected)).thenReturn(
               new ArrayList<>(roomsExpected)
        );

        //when
        List<RoomDTO> roomsActual = checkInService.assignRooms(bookingExpected.getBookingId().id());

        //then
        assertEquals(roomsExpected.size(), roomsActual.size());

        int i = 0;
        for(RoomDTO roomActual : roomsActual) {
            assertEquals(roomsExpected.get(i).getRoomCategory().getRoomCategoryName().name(), roomActual.categoryName());
            i++;
        }
    }

    @Test
    void given_missingBooking_when_assignRooms_then_BookingNotFoundExceptionIsThrown() {
        //given
        BookingId bookingIdExpected = new BookingId("1");

        Mockito.when(bookingRepository.bookingById(bookingIdExpected)).thenReturn(Optional.empty());

        //when ... then
        Exception exception = assertThrows(BookingNotFoundException.class, () -> {
            checkInService.assignRooms(bookingIdExpected.id());
        });

        String expectedMessage = "Booking with id " + bookingIdExpected.id() + " not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void given_booking_and_rooms_when_checkinbooking_then_roomsOccupied_and_bookingDeactivated_and_stayCreated() throws BookingNotFoundException, RoomNotFoundException {
        //given
        LocalDate checkInDateExpected = LocalDate.of(2021,12,1);
        LocalDate checkOutDateExpected = LocalDate.of(2021,12,2);
        BookingId bookingIdExpected = new BookingId("1");

        Guest guestExpected = Guest.create(
                new GuestId("1"),
                new FullName("Ali", "Cinar"),
                Gender.MALE,
                new Address("Hochschulstraße",
                        "1", "Dornbirn",
                        "6850", "Austria"),
                LocalDate.of(1997, 8, 27),
                "+43 676 123 456 789",
                "ali.cinar@students.fhv.at",
                0.0,
                Collections.emptyList()
        );

        List<Service> servicesExpected = List.of(
                Service.create(
                        new ServiceId("1"),
                        new ServiceName("TV"),
                        new Price(
                                new BigDecimal("100")
                        )
                ),
                Service.create(
                        new ServiceId("2"),
                        new ServiceName("Breakfast"),
                        new Price(
                                new BigDecimal("100")
                        )
                )
        );

        RoomCategory roomCategoryExpected = RoomCategory.create(new RoomCategoryId("1"),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        int roomCategoryAmountExpected = 3;

        int amountOfAdultsExpected = 2;
        int amountOfChildrenExpected = 0;
        String additionalInformationExpected = "Vegan";

        Booking bookingExpected = Booking.create(
                checkInDateExpected,
                checkOutDateExpected,
                bookingIdExpected,
                guestExpected,
                servicesExpected,
                amountOfAdultsExpected,
                amountOfChildrenExpected,
                additionalInformationExpected
        );

        bookingExpected.addRoomCategory(roomCategoryExpected, roomCategoryAmountExpected);

        List<String> roomNamesExpected = Arrays.asList("101", "102", "103");

        RoomStatus roomStatusExpected = RoomStatus.FREE;

        List<Room> roomsExpected = roomNamesExpected.stream()
                .map(name -> Room.create(name, roomStatusExpected, roomCategoryExpected))
                .collect(Collectors.toList());

        List<RoomDTO> roomDTOsExpected = roomsExpected.stream()
                .map(room -> RoomDTO.builder()
                                .withName(room.getName())
                                .withCategory(room.getRoomCategory().getRoomCategoryName().name())
                                .withStatus(RoomStatus.FREE.name())
                                .build())
                .collect(Collectors.toList());

        Mockito.when(bookingRepository.bookingById(bookingIdExpected)).thenReturn(Optional.of(bookingExpected));
        Mockito.when(roomRepository.roomByName(roomNamesExpected.get(0))).thenReturn(Optional.ofNullable(roomsExpected.get(0)));
        Mockito.when(roomRepository.roomByName(roomNamesExpected.get(1))).thenReturn(Optional.ofNullable(roomsExpected.get(1)));
        Mockito.when(roomRepository.roomByName(roomNamesExpected.get(2))).thenReturn(Optional.ofNullable(roomsExpected.get(2)));

        //when
        checkInService.checkIn(bookingIdExpected.id(), roomDTOsExpected);
        ArgumentCaptor<Stay> stayCaptor = ArgumentCaptor.forClass(Stay.class);
        Mockito.verify(stayRepository).add(stayCaptor.capture());
        Stay stayActual = stayCaptor.getValue();

        //then
        roomsExpected.forEach(room -> assertEquals(RoomStatus.OCCUPIED, room.getStatus()));
        assertFalse(bookingExpected.isActive());
        assertNotNull(stayActual);
        assertEquals(bookingExpected.getBookingId().id(), stayActual.getStayId().id());
    }

    @Test
    void given_missingBooking_when_checkInBooking_then_BookingNotFoundExceptionIsThrown() {
        //given
        LocalDate checkInDateExpected = LocalDate.of(2021,12,1);
        LocalDate checkOutDateExpected = LocalDate.of(2021,12,2);
        BookingId bookingIdExpected = new BookingId("1");

        Guest guestExpected = Guest.create(
                new GuestId("1"),
                new FullName("Ali", "Cinar"),
                Gender.MALE,
                new Address("Hochschulstraße",
                        "1", "Dornbirn",
                        "6850", "Austria"),
                LocalDate.of(1997, 8, 27),
                "+43 676 123 456 789",
                "ali.cinar@students.fhv.at",
                0.0,
                Collections.emptyList()
        );

        List<Service> servicesExpected = List.of(
                Service.create(
                        new ServiceId("1"),
                        new ServiceName("TV"),
                        new Price(
                                new BigDecimal("100")
                        )
                ),
                Service.create(
                        new ServiceId("2"),
                        new ServiceName("Breakfast"),
                        new Price(
                                new BigDecimal("100")
                        )
                )
        );

        RoomCategory roomCategoryExpected = RoomCategory.create(new RoomCategoryId("1"),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        int roomCategoryAmountExpected = 3;

        int amountOfAdultsExpected = 2;
        int amountOfChildrenExpected = 0;
        String additionalInformationExpected = "Vegan";

        Booking bookingExpected = Booking.create(
                checkInDateExpected,
                checkOutDateExpected,
                bookingIdExpected,
                guestExpected,
                servicesExpected,
                amountOfAdultsExpected,
                amountOfChildrenExpected,
                additionalInformationExpected
        );

        bookingExpected.addRoomCategory(roomCategoryExpected, roomCategoryAmountExpected);

        List<String> roomNamesExpected = Arrays.asList("101", "102", "103");

        RoomStatus roomStatusExpected = RoomStatus.FREE;

        List<Room> roomsExpected = roomNamesExpected.stream()
                .map(name -> Room.create(name, roomStatusExpected, roomCategoryExpected))
                .collect(Collectors.toList());

        List<RoomDTO> roomDTOsExpected = roomsExpected.stream()
                .map(room -> RoomDTO.builder()
                        .withName(room.getName())
                        .withCategory(room.getRoomCategory().getRoomCategoryName().name())
                        .withStatus(RoomStatus.FREE.name())
                        .build())
                .collect(Collectors.toList());

        Mockito.when(bookingRepository.bookingById(bookingIdExpected)).thenReturn(Optional.empty());

        //when ... then
        Exception exception = assertThrows(BookingNotFoundException.class, () -> {
            checkInService.checkIn(bookingIdExpected.id(), roomDTOsExpected);
        });

        String expectedMessage = "Booking with id " + bookingIdExpected.id() + " not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void given_missingRoom_when_checkInBooking_then_RoomNotFoundExceptionIsThrown() {
        //given
        LocalDate checkInDateExpected = LocalDate.of(2021,12,1);
        LocalDate checkOutDateExpected = LocalDate.of(2021,12,2);
        BookingId bookingIdExpected = new BookingId("1");

        Guest guestExpected = Guest.create(
                new GuestId("1"),
                new FullName("Ali", "Cinar"),
                Gender.MALE,
                new Address("Hochschulstraße",
                        "1", "Dornbirn",
                        "6850", "Austria"),
                LocalDate.of(1997, 8, 27),
                "+43 676 123 456 789",
                "ali.cinar@students.fhv.at",
                0.0,
                Collections.emptyList()
        );

        List<Service> servicesExpected = List.of(
                Service.create(
                        new ServiceId("1"),
                        new ServiceName("TV"),
                        new Price(
                                new BigDecimal("100")
                        )
                ),
                Service.create(
                        new ServiceId("2"),
                        new ServiceName("Breakfast"),
                        new Price(
                                new BigDecimal("100")
                        )
                )
        );

        RoomCategory roomCategoryExpected = RoomCategory.create(new RoomCategoryId("1"),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        int roomCategoryAmountExpected = 3;

        int amountOfAdultsExpected = 2;
        int amountOfChildrenExpected = 0;
        String additionalInformationExpected = "Vegan";

        Booking bookingExpected = Booking.create(
                checkInDateExpected,
                checkOutDateExpected,
                bookingIdExpected,
                guestExpected,
                servicesExpected,
                amountOfAdultsExpected,
                amountOfChildrenExpected,
                additionalInformationExpected
        );

        bookingExpected.addRoomCategory(roomCategoryExpected, roomCategoryAmountExpected);

        List<String> roomNamesExpected = Arrays.asList("101", "102", "103");

        RoomStatus roomStatusExpected = RoomStatus.FREE;

        List<Room> roomsExpected = roomNamesExpected.stream()
                .map(name -> Room.create(name, roomStatusExpected, roomCategoryExpected))
                .collect(Collectors.toList());

        List<RoomDTO> roomDTOsExpected = roomsExpected.stream()
                .map(room -> RoomDTO.builder()
                        .withName(room.getName())
                        .withCategory(room.getRoomCategory().getRoomCategoryName().name())
                        .withStatus(RoomStatus.FREE.name())
                        .build())
                .collect(Collectors.toList());

        Mockito.when(bookingRepository.bookingById(bookingIdExpected)).thenReturn(Optional.of(bookingExpected));
        Mockito.when(roomRepository.roomByName(roomNamesExpected.get(0))).thenReturn(Optional.empty());

        //when ... then
        Exception exception = assertThrows(RoomNotFoundException.class, () -> {
            checkInService.checkIn(bookingIdExpected.id(), roomDTOsExpected);
        });

        String expectedMessage = "Room with name " + roomNamesExpected.get(0) + " not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
