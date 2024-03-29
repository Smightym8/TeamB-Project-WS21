package at.fhv.se.hotel.integration.application;

import at.fhv.se.hotel.application.api.CheckInService;
import at.fhv.se.hotel.application.api.exception.BookingNotFoundException;
import at.fhv.se.hotel.application.api.exception.NotEnoughRoomsException;
import at.fhv.se.hotel.application.api.exception.RoomAlreadyOccupiedException;
import at.fhv.se.hotel.application.api.exception.RoomNotFoundException;
import at.fhv.se.hotel.application.dto.RoomDTO;
import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;
import at.fhv.se.hotel.domain.model.booking.BookingWithRoomCategory;
import at.fhv.se.hotel.domain.model.booking.BookingWithRoomCategoryId;
import at.fhv.se.hotel.domain.model.guest.*;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomName;
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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    @MockBean
    RoomCategoryRepository roomCategoryRepository;

    @Test
    void given_booking_when_assignRooms_then_returnexpectedrooms() throws BookingNotFoundException, NotEnoughRoomsException {
        // given
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
                "Nothing",
                "20210801001"
        );

        RoomCategory roomCategoryExpected = RoomCategory.create(new RoomCategoryId("1"),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room"));

        List<String> roomNamesExpected = Arrays.asList("101", "102", "103");

        RoomStatus roomStatusExpected = RoomStatus.FREE;

        List<Room> roomsExpected = roomNamesExpected.stream()
                .map(name -> Room.create(new RoomName(name), roomStatusExpected, roomCategoryExpected))
                .collect(Collectors.toList());

        bookingExpected.addRoomCategory(roomCategoryExpected, 3);

        Mockito.when(bookingRepository.bookingById(idExpected)).thenReturn(Optional.of(bookingExpected));
        Mockito.when(roomRepository.roomsByCategoryAndStatus(roomCategoryExpected.getRoomCategoryId(), roomStatusExpected)).thenReturn(
               new ArrayList<>(roomsExpected)
        );

        // when
        List<RoomDTO> roomsActual = checkInService.assignRooms(bookingExpected.getBookingId().id());

        // then
        assertEquals(roomsExpected.size(), roomsActual.size());

        int i = 0;
        for(RoomDTO roomActual : roomsActual) {
            assertEquals(roomsExpected.get(i).getRoomCategory().getRoomCategoryName().name(), roomActual.categoryName());
            i++;
        }
    }

    @Test
    void given_missingBooking_when_assignRooms_then_BookingNotFoundExceptionIsThrown() {
        // given
        BookingId bookingIdExpected = new BookingId("1");

        Mockito.when(bookingRepository.bookingById(bookingIdExpected)).thenReturn(Optional.empty());

        // when ... then
        Exception exception = assertThrows(BookingNotFoundException.class, () -> {
            checkInService.assignRooms(bookingIdExpected.id());
        });

        String expectedMessage = "Booking with id " + bookingIdExpected.id() + " not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void given_booking_and_rooms_when_checkinbooking_then_roomsOccupied_and_bookingDeactivated_and_stayCreated() throws BookingNotFoundException, RoomNotFoundException, RoomAlreadyOccupiedException {
        // given
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
        String bookingNumberExpected = checkInDateExpected.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001";

        Booking bookingExpected = Booking.create(
                checkInDateExpected,
                checkOutDateExpected,
                bookingIdExpected,
                guestExpected,
                servicesExpected,
                amountOfAdultsExpected,
                amountOfChildrenExpected,
                additionalInformationExpected,
                bookingNumberExpected
        );

        bookingExpected.addRoomCategory(roomCategoryExpected, roomCategoryAmountExpected);

        List<String> roomNamesExpected = Arrays.asList("101", "102", "103");

        RoomStatus roomStatusExpected = RoomStatus.FREE;

        List<Room> roomsExpected = roomNamesExpected.stream()
                .map(name -> Room.create(new RoomName(name), roomStatusExpected, roomCategoryExpected))
                .collect(Collectors.toList());

        StayId stayIdExpected = new StayId("1");

        Mockito.when(stayRepository.nextIdentity()).thenReturn(stayIdExpected);
        Mockito.when(bookingRepository.bookingById(bookingIdExpected)).thenReturn(Optional.of(bookingExpected));
        Mockito.when(roomRepository.roomByName(new RoomName(roomNamesExpected.get(0)))).thenReturn(Optional.ofNullable(roomsExpected.get(0)));
        Mockito.when(roomRepository.roomByName(new RoomName(roomNamesExpected.get(1)))).thenReturn(Optional.ofNullable(roomsExpected.get(1)));
        Mockito.when(roomRepository.roomByName(new RoomName(roomNamesExpected.get(2)))).thenReturn(Optional.ofNullable(roomsExpected.get(2)));

        // when
        checkInService.checkIn(bookingIdExpected.id(), roomNamesExpected);
        ArgumentCaptor<Stay> stayCaptor = ArgumentCaptor.forClass(Stay.class);
        Mockito.verify(stayRepository).add(stayCaptor.capture());
        Stay stayActual = stayCaptor.getValue();

        // then
        roomsExpected.forEach(room -> assertEquals(RoomStatus.OCCUPIED, room.getStatus()));
        assertFalse(bookingExpected.isActive());
        assertNotNull(stayActual);
        assertEquals(stayIdExpected.id(), stayActual.getStayId().id());
    }

    @Test
    void given_missingBooking_when_checkInBooking_then_BookingNotFoundExceptionIsThrown() {
        // given
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
        String bookingNumberExpected = checkInDateExpected.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001";

        Booking bookingExpected = Booking.create(
                checkInDateExpected,
                checkOutDateExpected,
                bookingIdExpected,
                guestExpected,
                servicesExpected,
                amountOfAdultsExpected,
                amountOfChildrenExpected,
                additionalInformationExpected,
                bookingNumberExpected
        );

        bookingExpected.addRoomCategory(roomCategoryExpected, roomCategoryAmountExpected);

        List<String> roomNamesExpected = Arrays.asList("101", "102", "103");

        RoomStatus roomStatusExpected = RoomStatus.FREE;

        List<Room> roomsExpected = roomNamesExpected.stream()
                .map(name -> Room.create(new RoomName(name), roomStatusExpected, roomCategoryExpected))
                .collect(Collectors.toList());

        List<RoomDTO> roomDTOsExpected = roomsExpected.stream()
                .map(room -> RoomDTO.builder()
                        .withName(room.getName().name())
                        .withCategory(room.getRoomCategory().getRoomCategoryName().name())
                        .withStatus(RoomStatus.FREE.name())
                        .build())
                .collect(Collectors.toList());

        Mockito.when(bookingRepository.bookingById(bookingIdExpected)).thenReturn(Optional.empty());

        // when ... then
        Exception exception = assertThrows(BookingNotFoundException.class, () -> {
            checkInService.checkIn(bookingIdExpected.id(), roomNamesExpected);
        });

        String expectedMessage = "Booking with id " + bookingIdExpected.id() + " not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void given_missingRoom_when_checkInBooking_then_RoomNotFoundExceptionIsThrown() {
        // given
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
        String bookingNumberExpected = checkInDateExpected.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001";

        Booking bookingExpected = Booking.create(
                checkInDateExpected,
                checkOutDateExpected,
                bookingIdExpected,
                guestExpected,
                servicesExpected,
                amountOfAdultsExpected,
                amountOfChildrenExpected,
                additionalInformationExpected,
                bookingNumberExpected
        );

        bookingExpected.addRoomCategory(roomCategoryExpected, roomCategoryAmountExpected);

        List<String> roomNamesExpected = Arrays.asList("101", "102", "103");

        RoomStatus roomStatusExpected = RoomStatus.FREE;

        List<Room> roomsExpected = roomNamesExpected.stream()
                .map(name -> Room.create(new RoomName(name), roomStatusExpected, roomCategoryExpected))
                .collect(Collectors.toList());

        Mockito.when(bookingRepository.bookingById(bookingIdExpected)).thenReturn(Optional.of(bookingExpected));
        Mockito.when(roomRepository.roomByName(new RoomName(roomNamesExpected.get(0)))).thenReturn(Optional.empty());

        // when ... then
        Exception exception = assertThrows(RoomNotFoundException.class, () -> {
            checkInService.checkIn(bookingIdExpected.id(), roomNamesExpected);
        });

        String expectedMessage = "Room with name " + roomNamesExpected.get(0) + " not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void given_notEnoughRooms_when_checkInBooking_then_NotEnoughRoomsExceptionIsThrown() {
        // given
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

        RoomCategory roomCategoryExpected1 = RoomCategory.create(new RoomCategoryId("1"),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        RoomCategory roomCategoryExpected2 = RoomCategory.create(new RoomCategoryId("2"),
                new RoomCategoryName("Double Room"),
                new Description("This is a double room")
        );

        int roomCategoryAmountExpected = 4;

        int amountOfAdultsExpected = 2;
        int amountOfChildrenExpected = 0;
        String additionalInformationExpected = "Vegan";
        String bookingNumberExpected = checkInDateExpected.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001";

        Booking bookingExpected = Booking.create(
                checkInDateExpected,
                checkOutDateExpected,
                bookingIdExpected,
                guestExpected,
                servicesExpected,
                amountOfAdultsExpected,
                amountOfChildrenExpected,
                additionalInformationExpected,
                bookingNumberExpected
        );

        bookingExpected.addRoomCategory(roomCategoryExpected1, roomCategoryAmountExpected);

        List<String> roomNamesExpected = Arrays.asList("101", "102", "103");

        RoomStatus roomStatusExpected = RoomStatus.FREE;

        List<Room> roomsExpected = roomNamesExpected.stream()
                .map(name -> Room.create(new RoomName(name), roomStatusExpected, roomCategoryExpected1))
                .collect(Collectors.toList());

        Mockito.when(bookingRepository.bookingById(bookingIdExpected)).thenReturn(Optional.of(bookingExpected));
        Mockito.when(roomRepository.roomsByCategoryAndStatus(roomCategoryExpected1.getRoomCategoryId(), roomStatusExpected))
                .thenReturn(new ArrayList<>(roomsExpected)
        );
        Mockito.when(roomCategoryRepository.findAllRoomCategories())
                .thenReturn(List.of(roomCategoryExpected1, roomCategoryExpected2)
        );
        Mockito.when(roomRepository.roomsByCategoryAndStatus(roomCategoryExpected1.getRoomCategoryId(), roomStatusExpected))
                .thenReturn(new ArrayList<>(roomsExpected));
        Mockito.when(roomRepository.roomsByCategoryAndStatus(roomCategoryExpected2.getRoomCategoryId(), roomStatusExpected))
                .thenReturn(Collections.emptyList());

        // when ... then
        Exception exception = assertThrows(NotEnoughRoomsException.class, () -> {
            checkInService.assignRooms(bookingIdExpected.id());
        });

        String expectedMessage = "There were not enough rooms";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void given_notEnoughRoomsForBookedCategories_when_checkInBooking_then_assignRoomFromOtherCategories() throws BookingNotFoundException, NotEnoughRoomsException {
        // given
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

        RoomCategory roomCategoryExpected1 = RoomCategory.create(new RoomCategoryId("1"),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        RoomCategory roomCategoryExpected2 = RoomCategory.create(new RoomCategoryId("2"),
                new RoomCategoryName("Double Room"),
                new Description("This is a double room")
        );

        List<RoomCategory> roomCategoriesExpected = List.of(roomCategoryExpected1, roomCategoryExpected2);

        int roomCategoryAmountExpected = 4;

        int amountOfAdultsExpected = 2;
        int amountOfChildrenExpected = 0;
        String additionalInformationExpected = "Vegan";
        String bookingNumberExpected = checkInDateExpected.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001";

        Booking bookingExpected = Booking.create(
                checkInDateExpected,
                checkOutDateExpected,
                bookingIdExpected,
                guestExpected,
                servicesExpected,
                amountOfAdultsExpected,
                amountOfChildrenExpected,
                additionalInformationExpected,
                bookingNumberExpected
        );

        bookingExpected.addRoomCategory(roomCategoryExpected1, roomCategoryAmountExpected);

        List<String> roomNamesExpected = Arrays.asList("101", "102", "103");

        RoomStatus roomStatusExpected = RoomStatus.FREE;

        List<Room> roomsExpected = roomNamesExpected.stream()
                .map(name -> Room.create(new RoomName(name), roomStatusExpected, roomCategoryExpected1))
                .collect(Collectors.toList());

        Room roomExpected = Room.create(new RoomName("201"), roomStatusExpected, roomCategoryExpected2);

        Mockito.when(bookingRepository.bookingById(bookingIdExpected)).thenReturn(Optional.of(bookingExpected));
        Mockito.when(roomRepository.roomsByCategoryAndStatus(roomCategoryExpected1.getRoomCategoryId(), roomStatusExpected)).thenReturn(
                new ArrayList<>(roomsExpected)
        );
        Mockito.when(roomRepository.roomsByCategoryAndStatus(roomCategoryExpected2.getRoomCategoryId(), roomStatusExpected)).thenReturn(
                new ArrayList<>(List.of(roomExpected))
        );
        Mockito.when(roomCategoryRepository.findAllRoomCategories()).thenReturn(roomCategoriesExpected);

        List<Room> assignedRoomsExpected = roomsExpected;
        assignedRoomsExpected.add(roomExpected);

        // when
        List<RoomDTO> assignedRoomsActual = checkInService.assignRooms(bookingExpected.getBookingId().id());

        // then
        assertEquals(assignedRoomsExpected.size(), assignedRoomsActual.size());

        int i = 0;
        for(RoomDTO roomActual : assignedRoomsActual) {
            assertEquals(assignedRoomsExpected.get(i).getRoomCategory().getRoomCategoryName().name(), roomActual.categoryName());
            i++;
        }
    }

    @Test
    void given_occupiedRoom_when_checkInBooking_then_RoomAlreadyOccupiedExceptionIsThrown() {
        // given
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

        int roomCategoryAmountExpected = 4;

        int amountOfAdultsExpected = 2;
        int amountOfChildrenExpected = 0;
        String additionalInformationExpected = "Vegan";
        String bookingNumberExpected = checkInDateExpected.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001";

        Booking bookingExpected = Booking.create(
                checkInDateExpected,
                checkOutDateExpected,
                bookingIdExpected,
                guestExpected,
                servicesExpected,
                amountOfAdultsExpected,
                amountOfChildrenExpected,
                additionalInformationExpected,
                bookingNumberExpected
        );

        List<String> roomNamesExpected = Arrays.asList("101", "102", "103");

        bookingExpected.addRoomCategory(roomCategoryExpected, roomCategoryAmountExpected);

        RoomStatus roomStatusFreeExpected = RoomStatus.FREE;

        Room freeRoom1 = Room.create(new RoomName("101"), roomStatusFreeExpected, roomCategoryExpected);

        Room freeRoom2 = Room.create(new RoomName("102"), roomStatusFreeExpected, roomCategoryExpected);

        Room occupiedRoom = Room.create(new RoomName("103"), RoomStatus.OCCUPIED, roomCategoryExpected);

        List<Room> roomsExpected = List.of(freeRoom1, freeRoom2, occupiedRoom);

        Mockito.when(bookingRepository.bookingById(bookingIdExpected)).thenReturn(Optional.of(bookingExpected));
        Mockito.when(roomRepository.roomByName(new RoomName(roomNamesExpected.get(0)))).thenReturn(Optional.of(roomsExpected.get(0)));
        Mockito.when(roomRepository.roomByName(new RoomName(roomNamesExpected.get(1)))).thenReturn(Optional.of(roomsExpected.get(1)));
        Mockito.when(roomRepository.roomByName(new RoomName(roomNamesExpected.get(2)))).thenReturn(Optional.of(roomsExpected.get(2)));

        // when ... then
        Exception exception = assertThrows(RoomAlreadyOccupiedException.class, () -> {
            checkInService.checkIn(bookingIdExpected.id(), roomNamesExpected);
        });

        String expectedMessage = "One of the assigned rooms was already occupied. Please start again with assigning rooms.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
