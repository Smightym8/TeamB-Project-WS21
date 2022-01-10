package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.CheckInService;
import at.fhv.se.hotel.application.api.exception.BookingNotFoundException;
import at.fhv.se.hotel.application.api.exception.RoomNotFoundException;
import at.fhv.se.hotel.application.dto.RoomDTO;
import at.fhv.se.hotel.domain.model.booking.Booking;
import at.fhv.se.hotel.domain.model.booking.BookingId;
import at.fhv.se.hotel.domain.model.booking.BookingWithRoomCategory;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomName;
import at.fhv.se.hotel.domain.model.room.RoomStatus;
import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.repository.BookingRepository;
import at.fhv.se.hotel.domain.repository.RoomRepository;
import at.fhv.se.hotel.domain.repository.StayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CheckInServiceImpl implements CheckInService {
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    StayRepository stayRepository;

    @Transactional
    @Override
    public List<RoomDTO> assignRooms(String bookingId) throws BookingNotFoundException {
        Booking booking = bookingRepository.bookingById(new BookingId(bookingId)).orElseThrow(
                () -> new BookingNotFoundException("Booking with id " + bookingId + " not found")
        );

        List<RoomDTO> assignedRooms = new ArrayList<>();

        // TODO: How to react if there are not enough rooms
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
                        .withName(freeRooms.get(0).getName().name())
                        .withCategory(freeRooms.get(0).getRoomCategory().getRoomCategoryName().name())
                        .withStatus(freeRooms.get(0).getStatus().name())
                        .build();

                freeRooms.remove(0);
                assignedRooms.add(dto);
            }
        }

        return assignedRooms;
    }

    @Transactional
    @Override
    public void checkIn(String bookingId, List<String> roomNames) throws BookingNotFoundException, RoomNotFoundException {
        //TODO: Check if rooms are occupied
        Booking booking = bookingRepository.bookingById(new BookingId(bookingId)).orElseThrow(
                () -> new BookingNotFoundException("Booking with id " + bookingId + " not found")
        );

        Map<Room, Boolean> assignedRooms = new HashMap<>();
        for(String roomName : roomNames) {
            Room room = roomRepository.roomByName(new RoomName(roomName)).orElseThrow(
                    () -> new RoomNotFoundException("Room with name " + roomName + " not found")
            );
            assignedRooms.put(room, false);

            // Change room status to occupied
            room.occupy();
        }

        // Create Stay with the rooms and the booking
        Stay stay = Stay.create(stayRepository.nextIdentity(), booking, assignedRooms);
        booking.deactivate();

        stayRepository.add(stay);
    }
}
