package at.fhv.se.hotel.integration.application;

import at.fhv.se.hotel.application.api.StayDetailsService;
import at.fhv.se.hotel.application.api.exception.ServiceNotFoundException;
import at.fhv.se.hotel.application.api.exception.StayNotFoundException;
import at.fhv.se.hotel.application.dto.StayDetailsDTO;
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
import at.fhv.se.hotel.domain.model.stay.StayId;
import at.fhv.se.hotel.domain.repository.StayRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StayDetailsServiceTest {

    @Autowired
    StayDetailsService stayDetailsService;

    @MockBean
    StayRepository stayRepository;

    @Test
    void given_staydetails_when_fetchingdetails_thenreturnequalsdetails() throws StayNotFoundException {
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
                0,
                Collections.emptyList()
        );

        String bookingNumberExpected = checkInExpected.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "001";

        Booking bookingExpected = Booking.create(
                checkInExpected,
                checkOutExpected,
                new BookingId(idExpected),
                guestExpected,
                servicesExpected,
                amountOfAdultsExpected,
                amountOfChildrenExpected,
                additionalInformationExpected,
                bookingNumberExpected
        );
        RoomCategory categoryExpected = RoomCategory.create(
                new RoomCategoryId("1"),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );
        Map<Room, Boolean> roomsExpected = Map.of(
                Room.create(new RoomName("S101"), RoomStatus.FREE,categoryExpected), false
        );

        StayId stayIdExpected = new StayId("1");
        Stay staysExpected = Stay.create(stayIdExpected, bookingExpected, roomsExpected);
        Mockito.when(stayRepository.stayById(stayIdExpected)).thenReturn(Optional.of(staysExpected));

        // when
        StayDetailsDTO stayDetailsDTOActual = stayDetailsService.detailsById(stayIdExpected.id());

        // then
        assertEquals(stayIdExpected.id(), stayDetailsDTOActual.id());
        assertEquals(guestExpected.getName().firstName(), stayDetailsDTOActual.guestFirstName());
        assertEquals(guestExpected.getName().lastName(), stayDetailsDTOActual.guestLastName());
        assertEquals(guestExpected.getAddress().streetName(), stayDetailsDTOActual.streetName());
        assertEquals(guestExpected.getAddress().streetNumber(), stayDetailsDTOActual.streetNumber());
        assertEquals(guestExpected.getAddress().zipCode(), stayDetailsDTOActual.zipCode());
        assertEquals(guestExpected.getAddress().city(), stayDetailsDTOActual.city());
        assertEquals(guestExpected.getAddress().country(), stayDetailsDTOActual.country());
        assertEquals(servicesExpected.size(), stayDetailsDTOActual.services().size());
        assertEquals(checkInExpected, stayDetailsDTOActual.checkInDate());
        assertEquals(checkOutExpected, stayDetailsDTOActual.checkOutDate());
        assertEquals(amountOfAdultsExpected, stayDetailsDTOActual.amountOfAdults());
        assertEquals(amountOfChildrenExpected, stayDetailsDTOActual.amountOfChildren());
        assertEquals(additionalInformationExpected, stayDetailsDTOActual.additionalInformation());
    }

    @Test
    public void given_missingStay_when_fetchingDetails_then_StayNotFoundExceptionIsThrown() {
        // given
        StayId stayIdExpected = new StayId("1");

        Mockito.when(stayRepository.stayById(stayIdExpected)).thenReturn(Optional.empty());

        // when ... then
        Exception exception = assertThrows(StayNotFoundException.class, () -> {
            stayDetailsService.detailsById(stayIdExpected.id());
        });

        String expectedMessage = "Stay with id " + stayIdExpected.id() + " not found";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
