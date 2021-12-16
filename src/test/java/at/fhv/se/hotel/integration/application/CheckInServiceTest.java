package at.fhv.se.hotel.integration.application;

import at.fhv.se.hotel.application.api.CheckInService;
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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class CheckInServiceTest {
    @Autowired
    private CheckInService checkInService;

    @Autowired
    GuestRepository guestRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    RoomCategoryRepository roomCategoryRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    StayRepository stayRepository;

    @Autowired
    EntityManager em;

    @AfterEach
    void cleanDatabase() {
        // TODO: Clear Database
        System.out.println("Clear database");
    }

    @Test
    void given_booking_when_assignrooms_then_returnexpectedrooms(){
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
                Collections.emptyList()
        );

        List<Service> servicesExpected = Arrays.asList(
                Service.create(new ServiceId("1"),
                        new ServiceName("TV"),
                        new Price(new BigDecimal("100"))),
                Service.create(new ServiceId("2"),
                        new ServiceName("Breakfast"),
                        new Price(new BigDecimal("100")))
        );
        Booking bookingExpected = Booking.create(
                LocalDate.of(2021, 8, 1),
                LocalDate.of(2021, 8, 10),
                new BookingId("1000"),
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

        guestRepository.add(guestExpected);
        servicesExpected.forEach(service -> serviceRepository.add(service));
        roomCategoryRepository.add(roomCategoryExpected);
        roomsExpected.forEach(room -> roomRepository.add(room));
        bookingRepository.add(bookingExpected);
        em.flush();

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
    void given_bookingDetails_when_checkinbooking_then_returnequaldetails(){
        //given
        String bookingIdStrExpected = "1234";
        LocalDate checkInDateExpected = LocalDate.of(2021,12,1);
        LocalDate checkOutDateExpected = LocalDate.of(2021,12,2);
        BookingId bookingIdExpected = new BookingId(bookingIdStrExpected);

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
                                .build())
                .collect(Collectors.toList());

        StayId idExpected = new StayId(bookingExpected.getBookingId().id());
        Stay stayExpected = Stay.create(bookingExpected, roomsExpected);

        guestRepository.add(guestExpected);
        servicesExpected.forEach(service -> serviceRepository.add(service));
        roomCategoryRepository.add(roomCategoryExpected);
        bookingRepository.add(bookingExpected);
        roomsExpected.forEach(room -> roomRepository.add(room));

        //when
        checkInService.checkIn(bookingIdStrExpected, roomDTOsExpected);
        Stay stayActual = stayRepository.stayById(idExpected).get();

        //then
        assertEquals(stayExpected.isActive(), stayActual.isActive());
        assertEquals(stayExpected.getCheckInDate(), stayActual.getCheckInDate());
        assertEquals(stayExpected.getCheckOutDate(), stayActual.getCheckOutDate());
        assertEquals(bookingExpected, stayActual.getBooking());
        assertEquals(stayExpected.getBooking().isActive(), stayActual.getBooking().isActive());
    }
}
