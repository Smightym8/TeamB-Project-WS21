package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.GuestListingService;
import at.fhv.se.hotel.application.dto.GuestDTO;
import at.fhv.se.hotel.domain.model.guest.Guest;
import at.fhv.se.hotel.domain.model.guest.GuestId;
import at.fhv.se.hotel.domain.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class GuestListingServiceImpl implements GuestListingService {

    @Autowired
    GuestRepository guestRepository;

    @Transactional(readOnly=true)
    @Override
    public List<GuestDTO> allGuests() {
        List<Guest> guests = guestRepository.findAllGuests();
        List<GuestDTO> dtos = new ArrayList<>();

        for(Guest guest : guests) {
            GuestDTO dto = GuestDTO.builder()
                    .withId(guest.getGuestId().id())
                    .withFirstName(guest.getName().firstName())
                    .withLastName(guest.getName().lastName())
                    .withStreetName(guest.getAddress().streetName())
                    .withStreetNumber(guest.getAddress().streetNumber())
                    .withCity(guest.getAddress().city())
                    .withZipCode(guest.getAddress().zipCode())
                    .withCountry(guest.getAddress().country())
                    .build();

            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public Optional<GuestDTO> findGuestById(String id) {
        Guest guest = guestRepository.guestById(new GuestId(id)).get();
        GuestDTO dto = GuestDTO.builder()
                .withId(guest.getGuestId().id())
                .withFirstName(guest.getName().firstName())
                .withLastName(guest.getName().lastName())
                .withStreetName(guest.getAddress().streetName())
                .withStreetNumber(guest.getAddress().streetNumber())
                .withCity(guest.getAddress().city())
                .withZipCode(guest.getAddress().zipCode())
                .withCountry(guest.getAddress().country())
                .build();

        return Optional.of(dto);
    }
}
