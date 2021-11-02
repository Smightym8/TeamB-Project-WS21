package at.fhv.se.hotel.application.impl;

import at.fhv.se.hotel.application.api.GuestListingService;
import at.fhv.se.hotel.application.dto.GuestDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class GuestListingServiceImpl implements GuestListingService {
    @Override
    public List<GuestDTO> allGuests() {
        final List<GuestDTO> guests = Arrays.asList(
                GuestDTO.builder()
                        .withFirstName("Ali")
                        .withLastName("Cinar")
                        .withBirthDate(LocalDate.of(1997, 8, 27))
                        .build(),
                GuestDTO.builder()
                        .withFirstName("Michael")
                        .withLastName("Spiegel")
                        .withBirthDate(LocalDate.of(1999, 3, 20))
                        .build()
        );

        return guests;
    }

    @Override
    public GuestDTO findGuestById(String id) {
        final GuestDTO guest = GuestDTO.builder()
                .withFirstName("Ali")
                .withLastName("Cinar")
                .withBirthDate(LocalDate.of(1997, 8, 27))
                .build();

        return guest;
    }

    @Override
    public List<GuestDTO> findGuestByName(String name) {
        final List<GuestDTO> guests = List.of(
                GuestDTO.builder()
                        .withFirstName("Michael")
                        .withLastName("Spiegel")
                        .withBirthDate(LocalDate.of(1999, 3, 20))
                        .build()
        );

        return guests;
    }
}
