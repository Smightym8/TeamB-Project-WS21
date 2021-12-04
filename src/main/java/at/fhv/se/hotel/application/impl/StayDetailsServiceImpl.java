package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.StayDetailsService;
import at.fhv.se.hotel.application.dto.StayDetailsDTO;
import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.model.stay.StayId;
import at.fhv.se.hotel.domain.repository.StayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StayDetailsServiceImpl implements StayDetailsService {

    @Autowired
    StayRepository stayRepository;

    @Override
    public StayDetailsDTO detailsById(String stayId) {
        Stay stay = stayRepository.stayById(new StayId(stayId)).get();

        List<String> rooms = new ArrayList<>();
        stay.getRooms().forEach(room -> rooms.add(room.getName()));

        Map<String, BigDecimal> services = new HashMap<>();
        stay.getServices().forEach(service ->
                services.put(
                        service.getServiceName().name(),
                        service.getServicePrice().price()
                )
        );

        StayDetailsDTO stayDetailsDTO = StayDetailsDTO.builder()
                .withId(stay.getStayId().id())
                .withGuestFirstName(stay.getGuest().getName().firstName())
                .withGuestLastName(stay.getGuest().getName().lastName())
                .withRooms(rooms)
                .withServices(services)
                .withCheckInDate(stay.getCheckInDate())
                .withCheckOutDate(stay.getCheckOutDate())
                .withAmountOfAdults(stay.getBooking().getAmountOfAdults())
                .withAmountOfChildren(stay.getBooking().getAmountOfChildren())
                .withAdditionalInformation(stay.getBooking().getAdditionalInformation())
                .build();

        return stayDetailsDTO;
    }
}
