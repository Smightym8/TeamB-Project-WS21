package at.fhv.se.hotel.unit.domain;

import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;
import at.fhv.se.hotel.domain.model.guest.*;
import at.fhv.se.hotel.domain.model.service.Price;
import at.fhv.se.hotel.domain.model.service.Service;
import at.fhv.se.hotel.domain.model.service.ServiceId;
import at.fhv.se.hotel.domain.model.service.ServiceName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookingTests {

    @Test
    void given_bookingdetails_when_createbooking_then_detailsequals() {
        // given
        LocalDate checkInDate = LocalDate.of(2021,12,1);
        LocalDate checkOutDate = LocalDate.of(2021,12,2);
        String bookingIdStr = "1";
        BookingId bookingId = new BookingId(bookingIdStr);

        Guest guest = Guest.create(
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
        Service tvService = Service.create(
                new ServiceId("1"),
                new ServiceName("TV"),
                new Price(
                        new BigDecimal("100")
                )
        );
        Service breakfastService = Service.create(
                new ServiceId("2"),
                new ServiceName("Breakfast"),
                new Price(
                        new BigDecimal("100")
                )
        );

        List<Service> services = List.of(tvService, breakfastService);
        int amountOfAdults = 2;
        int amountOfChildren = 0;
        String additionalInformation = "Vegan";

        // when
        Booking booking = Booking.create(
                checkInDate,
                checkOutDate,
                bookingId,
                guest,
                services,
                amountOfAdults,
                amountOfChildren,
                additionalInformation
        );

        // then
        assertEquals(checkInDate, booking.getCheckInDate());
        assertEquals(checkOutDate, booking.getCheckOutDate());
        assertTrue(booking.isActive());
        assertEquals(bookingId, booking.getBookingId());
        assertEquals(bookingIdStr, booking.getBookingId().id());
        assertEquals(guest, booking.getGuest());
        assertTrue(booking.getRoomCategories().isEmpty());
        assertEquals(services.size(), booking.getServices().size());
        assertEquals(amountOfAdults, booking.getAmountOfAdults());
        assertEquals(amountOfChildren, booking.getAmountOfChildren());
        assertEquals(additionalInformation, booking.getAdditionalInformation());

    }

    @Test
    void given_booking_when_deactivate_then_returntrue() {
        // given
        LocalDate checkInDate = LocalDate.of(2021,12,1);
        LocalDate checkOutDate = LocalDate.of(2021,12,2);
        String bookingIdStr = "1";
        BookingId bookingId = new BookingId(bookingIdStr);

        Guest guest = Guest.create(
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
        Service tvService = Service.create(
                new ServiceId("1"),
                new ServiceName("TV"),
                new Price(
                        new BigDecimal("100")
                )
        );
        Service breakfastService = Service.create(
                new ServiceId("2"),
                new ServiceName("Breakfast"),
                new Price(
                        new BigDecimal("100")
                )
        );

        List<Service> services = List.of(tvService, breakfastService);
        int amountOfAdults = 2;
        int amountOfChildren = 0;
        String additionalInformation = "Vegan";

        Booking booking = Booking.create(
                checkInDate,
                checkOutDate,
                bookingId,
                guest,
                services,
                amountOfAdults,
                amountOfChildren,
                additionalInformation
        );

        // when
        booking.deactivate();

        // then
        assertFalse(booking.isActive());
    }

    @Test
    void given_3bookings_2equals_1not_when_compare_then_2equals_1not() {
        // given
        LocalDate checkInDate1 = LocalDate.of(2021,12,1);
        LocalDate checkOutDate1 = LocalDate.of(2021,12,2);
        String bookingIdStr1 = "1";
        BookingId bookingId1 = new BookingId(bookingIdStr1);

        LocalDate checkInDate2 = LocalDate.of(2021,12,3);
        LocalDate checkOutDate2 = LocalDate.of(2021,12,4);
        String bookingIdStr2 = "2";
        BookingId bookingId2 = new BookingId(bookingIdStr2);

        Guest guest = Guest.create(
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
        Service tvService = Service.create(
                new ServiceId("1"),
                new ServiceName("TV"),
                new Price(
                        new BigDecimal("100")
                )
        );
        Service breakfastService = Service.create(
                new ServiceId("2"),
                new ServiceName("Breakfast"),
                new Price(
                        new BigDecimal("100")
                )
        );

        List<Service> services = List.of(tvService, breakfastService);
        int amountOfAdults = 2;
        int amountOfChildren = 0;
        String additionalInformation = "Vegan";

        Booking booking1 = Booking.create(
                checkInDate1,
                checkOutDate1,
                bookingId1,
                guest,
                services,
                amountOfAdults,
                amountOfChildren,
                additionalInformation
        );

        Booking booking2 = Booking.create(
                checkInDate2,
                checkOutDate2,
                bookingId2,
                guest,
                services,
                amountOfAdults,
                amountOfChildren,
                additionalInformation
        );

        Booking booking3 = Booking.create(
                checkInDate2,
                checkOutDate2,
                bookingId2,
                guest,
                services,
                amountOfAdults,
                amountOfChildren,
                additionalInformation
        );

        // when ... then
        assertNotEquals(booking1, booking2);
        assertNotEquals(booking1, booking3);
        assertEquals(booking2, booking3);
    }
}
