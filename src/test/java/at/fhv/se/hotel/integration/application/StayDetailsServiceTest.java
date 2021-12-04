package at.fhv.se.hotel.integration.application;

import at.fhv.se.hotel.application.api.StayDetailsService;
import at.fhv.se.hotel.application.dto.StayDetailsDTO;
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
import at.fhv.se.hotel.domain.model.stay.StayId;
import at.fhv.se.hotel.domain.repository.StayRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class StayDetailsServiceTest {

    @Autowired
    StayDetailsService stayDetailsService;

    @MockBean
    StayRepository stayRepository;

    @Test
    void given_staydetails_when_fetchingdetails_thenreturnequalsdetails() {
        //given
        String idExpected = "1";
        LocalDate checkInExpected = LocalDate.of(2021, 12, 1);
        LocalDate checkOutExpected = LocalDate.of(2021, 12, 3);
        int amountOfAdultsExpected = 2;
        int amountOfChildrenExpected = 1;
        String additionalInformationExpected = "Vegan";

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
                Collections.emptyList()
        );
        Booking bookingExpected = Booking.create(
                checkInExpected,
                checkOutExpected,
                new BookingId(idExpected),
                guestExpected,
                servicesExpected,
                amountOfAdultsExpected,
                amountOfChildrenExpected,
                additionalInformationExpected
        );
        RoomCategory categoryExpected = RoomCategory.create(
                new RoomCategoryId("1"),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );
        List<Room> roomsExpected = Arrays.asList(
                Room.create("S101", RoomStatus.FREE,categoryExpected)
        );

        Stay staysExpected = Stay.create(bookingExpected,roomsExpected);
        Mockito.when(stayRepository.stayById(new StayId(idExpected))).thenReturn(Optional.of(staysExpected));

        // when
        StayDetailsDTO stayDetailsDTOActual = stayDetailsService.detailsById(idExpected);

        // then
        assertEquals(idExpected, stayDetailsDTOActual.id());
        assertEquals(guestExpected.getName().firstName(), stayDetailsDTOActual.guestFirstName());
        assertEquals(guestExpected.getName().lastName(), stayDetailsDTOActual.guestLastName());
        assertEquals(roomsExpected.size(), stayDetailsDTOActual.rooms().size());
        assertEquals(servicesExpected.size(), stayDetailsDTOActual.services().size());
        assertEquals(checkInExpected, stayDetailsDTOActual.checkInDate());
        assertEquals(checkOutExpected, stayDetailsDTOActual.checkOutDate());
        assertEquals(amountOfAdultsExpected, stayDetailsDTOActual.amountOfAdults());
        assertEquals(amountOfChildrenExpected, stayDetailsDTOActual.amountOfChildren());
        assertEquals(additionalInformationExpected, stayDetailsDTOActual.additionalInformation());

        for(Room r : roomsExpected) {
            assertTrue(stayDetailsDTOActual.rooms().contains(r.getName()));
        }
    }
}
