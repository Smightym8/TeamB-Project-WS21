package at.fhv.se.hotel.integration.application;

import at.fhv.se.hotel.application.api.GuestListingService;
import at.fhv.se.hotel.application.dto.GuestDTO;
import at.fhv.se.hotel.domain.model.guest.*;
import at.fhv.se.hotel.domain.repository.GuestRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GuestListingServiceTests {
    @Autowired
    private GuestListingService guestListingService;

    @MockBean
    private GuestRepository guestRepository;

    @Test
    void given_3guests_when_3guestsinrepository_then_expect3namesequals() {
        // given
        Mockito.when(guestRepository.nextIdentity()).thenReturn(
                new GuestId(UUID.randomUUID().toString().toUpperCase()));

        List<Guest> guestsExpected = List.of(
                Guest.create(guestRepository.nextIdentity(),
                        new FullName("Michael", "Spiegel"),
                        Gender.MALE,
                        new Address("Hochschulstraße",
                                "1", "Dornbirn",
                                "6850", "Austria"),
                        LocalDate.of(1999, 3, 20),
                        "+43 660 123 456 789",
                        "michael.spiegel@students.fhv.at",
                        Collections.emptyList()
                ),
                Guest.create(guestRepository.nextIdentity(),
                        new FullName("Ali", "Cinar"),
                        Gender.MALE,
                        new Address("Hochschulstraße",
                                "1", "Dornbirn",
                                "6850", "Austria"),
                        LocalDate.of(1997, 8, 27),
                        "+43 676 123 456 789",
                        "ali.cinar@students.fhv.at",
                        Collections.emptyList()
                )
        );

        Mockito.when(guestRepository.findAllGuests()).thenReturn(guestsExpected);

        // when
        List<GuestDTO> guestsActual = guestListingService.allGuests();

        // then
        for(int i = 0; i < guestsActual.size(); i++) {
            assertEquals(guestsExpected.get(i).getName().firstName(), guestsActual.get(i).firstName());
            assertEquals(guestsExpected.get(i).getName().lastName(), guestsActual.get(i).lastName());
        }
    }
}
