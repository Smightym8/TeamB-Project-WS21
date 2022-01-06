package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.StayDetailsService;
import at.fhv.se.hotel.application.api.exception.StayNotFoundException;
import at.fhv.se.hotel.application.dto.StayDetailsDTO;
import at.fhv.se.hotel.domain.model.stay.Stay;
import at.fhv.se.hotel.domain.model.stay.StayId;
import at.fhv.se.hotel.domain.repository.RoomCategoryPriceRepository;
import at.fhv.se.hotel.domain.repository.StayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class StayDetailsServiceImpl implements StayDetailsService {

    @Autowired
    StayRepository stayRepository;

    @Autowired
    RoomCategoryPriceRepository roomCategoryPriceRepository;

    @Override
    public StayDetailsDTO detailsById(String stayId) throws StayNotFoundException {
        Stay stay = stayRepository.stayById(new StayId(stayId)).orElseThrow(
                () -> new StayNotFoundException("Stay with id " + stayId + " not found")
        );

        Map<String, String> roomsWithCategories = new HashMap<>();
        stay.getRooms().forEach((room, isPaid) -> {
            if (!isPaid) {
                roomsWithCategories.put(
                        room.getName().name(),
                        room.getRoomCategory().getRoomCategoryName().name()
                );
            }
        });

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
                .withStreetName(stay.getGuest().getAddress().streetName())
                .withStreetNumber(stay.getGuest().getAddress().streetNumber())
                .withZipCode(stay.getGuest().getAddress().zipCode())
                .withCity(stay.getGuest().getAddress().city())
                .withCountry(stay.getGuest().getAddress().country())
                .withRoomsWithCategories(roomsWithCategories)
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
