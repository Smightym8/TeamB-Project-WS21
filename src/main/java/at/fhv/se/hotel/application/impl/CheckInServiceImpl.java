package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.CheckInService;
import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomStatus;
import at.fhv.se.hotel.domain.model.roomcategory.RoomCategory;
import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.repository.BookingRepository;
import at.fhv.se.hotel.domain.repository.RoomRepository;
import at.fhv.se.hotel.domain.repository.StayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CheckInServiceImpl implements CheckInService {
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    StayRepository stayRepository;

    @Override
    public void checkIn(String bookingId) {
/*

        Booking booking = bookingRepository.bookingById(new BookingId(bookingId)).get();

        List<Room> assignedRooms = new ArrayList<>();
        // Get rooms according to the booked category and by the booked amount
        // TODO: Implement logic to get rooms

        for(RoomCategory rc : booking.getRoomCategories()) {
            Room room = roomRepository.roomsByCategoryAndStatus(rc.getRoomCategoryId(), RoomStatus.FREE).get(0);
            assignedRooms.add(room);
        }

        // Create Stay with the rooms and the booking
        Stay stay = Stay.create(booking, assignedRooms);

        stayRepository.add(stay);
*/

    }
}
