package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.StayListingService;
import at.fhv.se.hotel.application.dto.StayListingDTO;
import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.repository.StayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the implementation of the interface {@link StayListingService}
 * It provides the functionality to get all stays.
 */
@Component
public class StayListingServiceImpl implements StayListingService {

    @Autowired
    private StayRepository stayRepository;

    /**
     * This method provides all stays.
     * @return a list of StayListingDTO objects.
     */
    @Override
    public List<StayListingDTO> allStays() {
        List<Stay> stays = stayRepository.findAllStays();
        List<StayListingDTO> stayDTOs = new ArrayList<>();

        for (Stay stay : stays){
            List<String> rooms = new ArrayList<>();
            stay.getRooms().forEach((k, v) -> rooms.add(k.getName().name()));

            StayListingDTO stayDTO = StayListingDTO.builder()
                    .withId(stay.getStayId().id())
                    .withGuestFirstName(stay.getGuest().getName().firstName())
                    .withGuestLastName(stay.getGuest().getName().lastName())
                    .withCheckOutDate(stay.getCheckOutDate())
                    .withRooms(rooms)
                    .withAmountOfPersons(stay.getBooking().getAmountOfAdults() + stay.getBooking().getAmountOfChildren())
                    .withIsActive(stay.isActive())
                    .build();

            stayDTOs.add(stayDTO);
        }

        return stayDTOs;
    }
}
