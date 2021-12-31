package at.fhv.se.hotel.integration.application;

import at.fhv.se.hotel.application.api.StayListingService;
import at.fhv.se.hotel.application.dto.StayListingDTO;
import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;
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
import at.fhv.se.hotel.domain.repository.StayRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StayListingServiceTests {
    @Autowired
    private StayListingService stayListingService;

    @MockBean
    private StayRepository stayRepository;

    @Test
    void given_3staysinrepository_when_fetchingallstays_then3matchingstayid(){
        //given
        List<Service> servicesExpected = Arrays.asList(
                Service.create(
                        new ServiceId("1"),
                        new ServiceName("TV"),
                        new Price(new BigDecimal("100"))),
                Service.create(
                        new ServiceId("2"),
                        new ServiceName("Breakfast"),
                        new Price(new BigDecimal("100")))
        );
        Guest guestExpected = Guest.create(new GuestId("1"),
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
        Booking bookingExpected = Booking.create(
                    LocalDate.now(),
                    LocalDate.now().plusDays(10),
                    new BookingId("1"),
                    guestExpected,
                    servicesExpected,
                2,
                1,
                ""
                );
        Booking bookingExpected2 = Booking.create(
                        LocalDate.now(),
                        LocalDate.now().plusDays(10),
                        new BookingId("2"),
                        guestExpected,
                        servicesExpected,
                2,
                1,
                ""
                );
        Booking bookingExpected3 = Booking.create(
                        LocalDate.now(),
                        LocalDate.now().plusDays(10),
                        new BookingId("3"),
                        guestExpected,
                        servicesExpected,
                2,
                1,
                ""
                );
        RoomCategory categoryExpected = RoomCategory.create(
                new RoomCategoryId("1"),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        Map<Room, Boolean> roomsExpected = Map.of(
                Room.create(new RoomName("single Room"),RoomStatus.FREE,categoryExpected), false,
                Room.create(new RoomName("double Room"),RoomStatus.FREE,categoryExpected), false
        );

        List<Stay> staysExpected = List.of(
                Stay.create(bookingExpected,roomsExpected),
                Stay.create(bookingExpected2,roomsExpected),
                Stay.create(bookingExpected3,roomsExpected)
        );

        Mockito.when(stayRepository.findAllStays()).thenReturn(staysExpected);

        // when
        List<StayListingDTO> staysActual = stayListingService.allStays();

        // then
        assertEquals(staysExpected.size(),staysActual.size());
        for (int i = 0; i < staysExpected.size(); i++){
            assertEquals(staysExpected.get(i).getStayId().id(), staysActual.get(i).id());
            assertEquals(staysExpected.get(i).getBooking().getGuest().getName().firstName(), staysActual.get(i).guestFirstName());
            assertEquals(staysExpected.get(i).getBooking().getGuest().getName().lastName(), staysActual.get(i).guestLastName());
            assertEquals(staysExpected.get(i).getBooking().getCheckOutDate(), staysActual.get(i).checkOutDate());
            assertEquals(staysExpected.get(i).isActive(), staysActual.get(i).isActive());
            assertEquals(
                    staysExpected.get(i).getBooking().getAmountOfAdults() + staysExpected.get(i).getBooking().getAmountOfChildren(),
                    staysActual.get(i).amountOfPersons()
            );
            assertEquals(staysExpected.get(i).getRooms().size(), staysActual.get(i).rooms().size());
        }
    }
}