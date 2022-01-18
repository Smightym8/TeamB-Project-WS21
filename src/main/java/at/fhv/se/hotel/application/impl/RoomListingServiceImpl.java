package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.RoomListingService;
import at.fhv.se.hotel.application.api.exception.RoomNotFoundException;
import at.fhv.se.hotel.application.dto.RoomDTO;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.model.room.RoomName;
import at.fhv.se.hotel.domain.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the implementation of the interface {@link RoomListingService}
 * It provides the functionality to get all rooms.
 */
@Component
public class RoomListingServiceImpl implements RoomListingService {
    @Autowired
    RoomRepository roomRepository;

    /**
     * This method provides all rooms.
     * @return a list of RoomDTO objects.
     */
    @Transactional(readOnly = true)
    @Override
    public List<RoomDTO> allRooms() {
        List<Room> rooms = roomRepository.findAllRooms();
        List<RoomDTO> roomDTOs = new ArrayList<>();

        for(Room room : rooms) {
            RoomDTO dto = RoomDTO.builder()
                    .withName(room.getName().name())
                    .withCategory(room.getRoomCategory().getRoomCategoryName().name())
                    .withStatus(room.getStatus().name())
                    .build();

            roomDTOs.add(dto);
        }

        return roomDTOs;
    }

    /**
     * This method provides all free rooms.
     * @return a list of RoomDTO objects.
     */
    @Transactional(readOnly = true)
    @Override
    public List<RoomDTO> allFreeRooms() {
        List<Room> freeRooms = roomRepository.findAllFreeRooms();
        List<RoomDTO> freeRoomsDTOs = new ArrayList<>();

        for(Room room : freeRooms) {
            RoomDTO dto = RoomDTO.builder()
                    .withName(room.getName().name())
                    .withCategory(room.getRoomCategory().getRoomCategoryName().name())
                    .withStatus(room.getStatus().name())
                    .build();

            freeRoomsDTOs.add(dto);
        }

        return freeRoomsDTOs;
    }

    /**
     * This method provides a room by room name.
     * @param name contains the name of the room.
     * @return a RoomDTO object.
     * @throws RoomNotFoundException if room could not be found.
     */
    @Transactional(readOnly = true)
    @Override
    public RoomDTO roomByName(String name) throws RoomNotFoundException {
        Room room = roomRepository.roomByName(new RoomName(name)).orElseThrow(
                () -> new RoomNotFoundException("Room " + name + " not found")
        );

        RoomDTO roomDTO = RoomDTO.builder()
                .withName(room.getName().name())
                .withCategory(room.getRoomCategory().getRoomCategoryName().name())
                .withStatus(room.getStatus().name())
                .build();

        return roomDTO;
    }
}
