package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.GuestListingService;
import at.fhv.se.hotel.application.dto.GuestDTO;
import at.fhv.se.hotel.domain.model.guest.Guest;
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
                    .withBirthDate(guest.getBirthDate())
                    .withFirstName(guest.getName().firstName())
                    .withLastName(guest.getName().lastName())
                    .build();

            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public Optional<GuestDTO> findGuestById(String id) {
        final List<GuestDTO> guests = Arrays.asList(
                GuestDTO.builder()
                        .withId("1")
                        .withFirstName("Ali")
                        .withLastName("Cinar")
                        .withBirthDate(LocalDate.of(1997, 8, 27))
                        .build(),
                GuestDTO.builder()
                        .withId("2")
                        .withFirstName("Michael")
                        .withLastName("Spiegel")
                        .withBirthDate(LocalDate.of(1999, 3, 20))
                        .build()
        );

        GuestDTO guest = null;

        for (GuestDTO g : guests) {
            if(g.id().equals(id)){
                guest = g;
            }
        }

        return Optional.ofNullable(guest);
    }

    @Override
    public List<GuestDTO> findGuestByName(String name) {
        final List<GuestDTO> guests = List.of(
                GuestDTO.builder()
                        .withId("2")
                        .withFirstName("Michael")
                        .withLastName("Spiegel")
                        .withBirthDate(LocalDate.of(1999, 3, 20))
                        .build()
        );

        return guests;
    }
}
