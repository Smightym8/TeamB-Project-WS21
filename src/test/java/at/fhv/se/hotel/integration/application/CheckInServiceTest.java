package at.fhv.se.hotel.integration.application;

import at.fhv.se.hotel.application.api.CheckInService;
import at.fhv.se.hotel.application.dto.RoomDTO;
import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;
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
import at.fhv.se.hotel.domain.repository.BookingRepository;
import at.fhv.se.hotel.domain.repository.RoomRepository;
import at.fhv.se.hotel.domain.repository.StayRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

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
public class CheckInServiceTest {
    @Autowired
    private CheckInService checkInService;

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private RoomRepository roomRepository;

    @Autowired
    private StayRepository stayRepository;

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
                new BookingId("1"),
                guestExpected,
                servicesExpected,
                2,
                1,
                "Nothing"
        );

        //when
        List<RoomDTO> roomsActual = checkInService.assignRooms(bookingExpected.getBookingId().id());

        //then
        assertEquals(bookingExpected.getRoomCategories(),roomsActual.get(0).categoryName());
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
        Service tvServiceExpected = Service.create(
                new ServiceId("1"),
                new ServiceName("TV"),
                new Price(
                        new BigDecimal("100")
                )
        );
        Service breakfastServiceExpected = Service.create(
                new ServiceId("2"),
                new ServiceName("Breakfast"),
                new Price(
                        new BigDecimal("100")
                )
        );

        List<Service> servicesExpected = List.of(tvServiceExpected, breakfastServiceExpected);
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

        RoomCategory singleRoom = RoomCategory.create(
                new RoomCategoryId("1"),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        int roomCategoryAmountExpected = 3;

        bookingExpected.addRoomCategory(singleRoom, roomCategoryAmountExpected);

        List<String> roomNamesExpected = Arrays.asList("101", "102", "103");

        RoomCategory roomCategoryExpected = RoomCategory.create(new RoomCategoryId("1"),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room"));

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

        Stay stayExpected = Stay.create(bookingExpected, roomsExpected);

        Mockito.when(bookingRepository.bookingById(bookingIdExpected)).thenReturn(Optional.of(bookingExpected));

        Mockito.when(roomRepository.roomByName(roomDTOsExpected.get(0).name())).thenReturn(Optional.of(roomsExpected.get(0)));
        Mockito.when(roomRepository.roomByName(roomDTOsExpected.get(1).name())).thenReturn(Optional.of(roomsExpected.get(1)));
        Mockito.when(roomRepository.roomByName(roomDTOsExpected.get(2).name())).thenReturn(Optional.of(roomsExpected.get(2)));
        //Mockito.doNothing().when(stayRepository).add(stayExpected);


        //when
        checkInService.checkIn(bookingIdStrExpected, roomDTOsExpected);

        //then
        List<Stay> staysActual = stayRepository.findAllStays();

        assertEquals(staysActual.get(0).isActive(),stayExpected.isActive());
        assertEquals(staysActual.get(0).getBooking().isActive(), stayExpected.getBooking().isActive());

    }
}
