package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.StayListingService;
import at.fhv.se.hotel.application.dto.StayDTO;
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
    public List<StayDTO> allStays() {
        List<Stay> stays = stayRepository.findAllStays();
        List<StayDTO> stayDTOs = new ArrayList<>();

        for (Stay stay : stays){
            StayDTO stayDTO = StayDTO.builder()
                    .withId(stay.getStayId().id())
                    .withGuestFirstName(stay.getGuest().getName().firstName())
                    .withGuestLastName(stay.getGuest().getName().lastName())
                    .withCheckInDate(stay.getCheckInDate())
                    .withCheckOutDate(stay.getCheckOutDate())
                    .build();
            stayDTOs.add(stayDTO);
        }
        return stayDTOs;
    }
}
