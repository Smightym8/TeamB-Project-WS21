package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.StayListingService;
import at.fhv.se.hotel.application.dto.StayListingDTO;
import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.repository.StayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StayListingServiceImpl implements StayListingService {

    @Autowired
    StayRepository stayRepository;

    @Override
    public List<StayListingDTO> allStays() {
        List<Stay> stays = stayRepository.findAllStays();
        List<StayListingDTO> stayDTOs = new ArrayList<>();

        for (Stay stay : stays){
            List<String> rooms = new ArrayList<>();
            stay.getRooms().forEach(room -> rooms.add(room.getName()));

            StayListingDTO stayDTO = StayListingDTO.builder()
                    .withId(stay.getStayId().id())
                    .withGuestFirstName(stay.getGuest().getName().firstName())
                    .withGuestLastName(stay.getGuest().getName().lastName())
                    .withCheckOutDate(stay.getCheckOutDate())
                    .withRooms(rooms)
                    .withIsActive(stay.isActive())
                    .build();

            stayDTOs.add(stayDTO);
        }

        return stayDTOs;
    }
}
