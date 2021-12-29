package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.RoomListingService;
import at.fhv.se.hotel.application.dto.RoomDTO;
import at.fhv.se.hotel.domain.model.room.Room;
import at.fhv.se.hotel.domain.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoomListingServiceImpl implements RoomListingService {
    @Autowired
    RoomRepository roomRepository;

    @Transactional(readOnly = true)
    @Override
    public List<RoomDTO> allRooms() {
        List<Room> rooms = roomRepository.findAllRooms();
        List<RoomDTO> roomDTOs = new ArrayList<>();

        for(Room room : rooms) {
            RoomDTO dto = RoomDTO.builder()
                    .withName(room.getName())
                    .withCategory(room.getRoomCategory().getRoomCategoryName().name())
                    .withStatus(room.getStatus().name())
                    .build();

            roomDTOs.add(dto);
        }

        return roomDTOs;
    }
}
