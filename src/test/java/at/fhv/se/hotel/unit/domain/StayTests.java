package at.fhv.se.hotel.unit.domain;

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
import org.apache.xpath.operations.Bool;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class StayTests {

    @Test
    void given_stay_details_when_creating_stay_then_equals_details() {
        // when
        Service tvService = Service.create(
                new ServiceId("1"),
                new ServiceName("TV"),
                new Price(new BigDecimal("100"))
        );
        Service breakfastService = Service.create(
                new ServiceId("2"),
                new ServiceName("Breakfast"),
                new Price(new BigDecimal("100"))
        );

        RoomCategory singleRoom = RoomCategory.create(
                new RoomCategoryId("1"),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        Guest guest = Guest.create(
                new GuestId("1"),
                new FullName("Michael", "Spiegel"),
                Gender.MALE,
                new Address("Hochschulstraße",
                        "1", "Dornbirn",
                        "6850", "Austria"),
                LocalDate.of(1999, 3, 20),
                "+43 660 123 456 789",
                "michael.spiegel@students.fhv.at",
                0,
                Collections.emptyList()
        );

        List<Service> services = List.of(tvService, breakfastService);

        LocalDate checkInDate = LocalDate.of(2021,12,1);
        LocalDate checkOutDate = LocalDate.of(2021,12,4);

        Booking booking = Booking.create(
                checkInDate,
                checkOutDate,
                new BookingId("1"),
                guest,
                services,
                1,
                0,
                "Extra pillow"
        );

        int amount = 1;

        booking.addRoomCategory(singleRoom, amount);

        Room room1 = Room.create(
                "101",
                RoomStatus.FREE,
                singleRoom);

        Room room2 = Room.create(
                "102",
                RoomStatus.FREE,
                singleRoom);

        Room room3 = Room.create(
                "103",
                RoomStatus.FREE,
                singleRoom);

        String stayIdStr = "1";
        StayId stayId = new StayId(stayIdStr);

        Map<Room, Boolean> rooms = Map.of(
                room1, false,
                room2, false,
                room3, false
        );

        // when
        Stay stay = Stay.create(
                booking,
                rooms
        );

        // then
        assertEquals(stayId, stay.getStayId());
        assertEquals(stayIdStr, stay.getStayId().id());
        assertEquals(rooms.size(), stay.getRooms().size());
        assertEquals(booking, stay.getBooking());
        assertEquals(guest, stay.getGuest());
        assertEquals(checkInDate, stay.getCheckInDate());
        assertEquals(checkOutDate, stay.getCheckOutDate());
        assertEquals(services, stay.getServices());
        assertTrue(stay.isActive());

        for (Service s : services) {
            assertTrue(stay.getServices().contains(s));
        }
    }

    @Test
    void given_stay_when_deactivating_then_return_true() {
        // when
        Service tvService = Service.create(
                new ServiceId("1"),
                new ServiceName("TV"),
                new Price(new BigDecimal("100"))
        );
        Service breakfastService = Service.create(
                new ServiceId("2"),
                new ServiceName("Breakfast"),
                new Price(new BigDecimal("100"))
        );

        RoomCategory singleRoom = RoomCategory.create(
                new RoomCategoryId("1"),
                new RoomCategoryName("Single Room"),
                new Description("This is a single room")
        );

        Guest guest = Guest.create(
                new GuestId("1"),
                new FullName("Michael", "Spiegel"),
                Gender.MALE,
                new Address("Hochschulstraße",
                        "1", "Dornbirn",
                        "6850", "Austria"),
                LocalDate.of(1999, 3, 20),
                "+43 660 123 456 789",
                "michael.spiegel@students.fhv.at",
                0,
                Collections.emptyList()
        );

        List<Service> services = List.of(tvService, breakfastService);

        LocalDate checkInDate = LocalDate.of(2021,12,1);
        LocalDate checkOutDate = LocalDate.of(2021,12,4);

        Booking booking = Booking.create(
                checkInDate,
                checkOutDate,
                new BookingId("1"),
                guest,
                services,
                1,
                0,
                "Extra pillow"
        );

        int amount = 1;

        booking.addRoomCategory(singleRoom, amount);

        Room room1 = Room.create(
                "101",
                RoomStatus.FREE,
                singleRoom);

        Room room2 = Room.create(
                "102",
                RoomStatus.FREE,
                singleRoom);

        Room room3 = Room.create(
                "103",
                RoomStatus.FREE,
                singleRoom);

        String stayIdStr = "1";
        StayId stayId = new StayId(stayIdStr);

        Map<Room, Boolean> rooms = Map.of(
                room1, false,
                room2, false,
                room3, false
        );

        Stay stay = Stay.create(
                booking,
                rooms
        );

        // when
        stay.deactivate();

        // then
        assertFalse(stay.isActive());
    }
}
