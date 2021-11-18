package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.CheckInService;
import at.fhv.se.hotel.application.dto.RoomDTO;
import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;
import at.fhv.se.hotel.domain.model.booking.BookingWithRoomCategory;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomStatus;
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
    public List<RoomDTO> assignRooms(String bookingId) {
        Booking booking = bookingRepository.bookingById(new BookingId(bookingId)).get();
        List<RoomDTO> assignedRooms = new ArrayList<>();

        for(BookingWithRoomCategory brc : booking.getRoomCategories()) {
            // Get free rooms from current category
            List<Room> freeRooms = roomRepository.roomsByCategoryAndStatus(
                    brc.getRoomCategory().getRoomCategoryId(),
                    RoomStatus.FREE
            );

            // Add booked amount of rooms
            for(int i = 0; i < brc.getAmount(); i++) {
                // Build dto and add it to assignedRooms
                RoomDTO dto = RoomDTO.builder()
                        .withName(freeRooms.get(0).getName())
                        .build();

                freeRooms.remove(0);
                assignedRooms.add(dto);
            }
        }

        return assignedRooms;
    }

    @Override
    public void checkIn(String bookingId, List<RoomDTO> rooms) {
        //TODO: Check if rooms are occupied
        Booking booking = bookingRepository.bookingById(new BookingId(bookingId)).get();

        List<Room> assignedRooms = new ArrayList<>();
        for(RoomDTO r : rooms) {
            Room room = roomRepository.roomByName(r.name()).get();
            assignedRooms.add(room);

            // Change room status to occupied
            roomRepository.occupyRoom(room);
        }

        // Create Stay with the rooms and the booking
        Stay stay = Stay.create(booking, assignedRooms);

        stayRepository.add(stay);
    }
}
