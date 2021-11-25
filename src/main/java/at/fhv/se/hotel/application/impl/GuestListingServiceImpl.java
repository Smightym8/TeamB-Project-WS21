package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.GuestListingService;
import at.fhv.se.hotel.application.dto.GuestDTO;
import at.fhv.se.hotel.domain.model.guest.Guest;
import at.fhv.se.hotel.domain.model.guest.GuestId;
import at.fhv.se.hotel.domain.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
                    //.withBirthDate(guest.getBirthDate())
                    .withStreetName(guest.getAddress().streetName())
                    .withStreetNumber(guest.getAddress().streetNumber())
                    .withCity(guest.getAddress().city())
                    .withZipCode(guest.getAddress().zipCode())
                    .withCountry(guest.getAddress().country())
                    .withFirstName(guest.getName().firstName())
                    .withLastName(guest.getName().lastName())
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
                //.withBirthDate(guest.getBirthDate())
                .withStreetName(guest.getAddress().streetName())
                .withStreetNumber(guest.getAddress().streetNumber())
                .withCity(guest.getAddress().city())
                .withZipCode(guest.getAddress().zipCode())
                .withCountry(guest.getAddress().country())
                .withFirstName(guest.getName().firstName())
                .withLastName(guest.getName().lastName())
                .build();

        return Optional.of(dto);
    }

    @Override
    public List<GuestDTO> findGuestByName(String name) {
        final List<GuestDTO> guests = List.of(
                GuestDTO.builder()
                        .withId("2")
                        .withFirstName("Michael")
                        .withLastName("Spiegel")
                        //.withBirthDate(LocalDate.of(1999, 3, 20))
                        .withStreetName("Hochschulstraße")
                        .withStreetNumber("1")
                        .withCity("Dornbirn")
                        .withZipCode("6850")
                        .withCountry("Austria")
                        .build()
        );

        return guests;
    }
}
