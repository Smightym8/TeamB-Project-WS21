package at.fhv.se.hotel.integration.application;

import at.fhv.se.hotel.application.api.GuestListingService;
import at.fhv.se.hotel.application.api.exception.GuestNotFoundException;
import at.fhv.se.hotel.application.dto.GuestDetailsDTO;
import at.fhv.se.hotel.application.dto.GuestListingDTO;
import at.fhv.se.hotel.domain.model.guest.*;
import at.fhv.se.hotel.domain.repository.GuestRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
                        0,
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
                        0,
                        Collections.emptyList()
                )
        );

        Mockito.when(guestRepository.findAllGuests()).thenReturn(guestsExpected);

        // when
        List<GuestListingDTO> guestsActual = guestListingService.allGuests();

        // then
        assertEquals(guestsExpected.size(), guestsActual.size());
        for(int i = 0; i < guestsActual.size(); i++) {
            assertEquals(guestsExpected.get(i).getGuestId().id(), guestsActual.get(i).id());
            assertEquals(guestsExpected.get(i).getName().firstName(), guestsActual.get(i).firstName());
            assertEquals(guestsExpected.get(i).getName().lastName(), guestsActual.get(i).lastName());
            assertEquals(guestsExpected.get(i).getAddress().streetName(), guestsActual.get(i).streetName());
            assertEquals(guestsExpected.get(i).getAddress().streetNumber(), guestsActual.get(i).streetNumber());
            assertEquals(guestsExpected.get(i).getAddress().city(), guestsActual.get(i).city());
            assertEquals(guestsExpected.get(i).getAddress().zipCode(), guestsActual.get(i).zipCode());
            assertEquals(guestsExpected.get(i).getAddress().country(), guestsActual.get(i).country());
            assertEquals(guestsExpected.get(i).getMailAddress(), guestsActual.get(i).mailAddress());
            assertEquals(guestsExpected.get(i).getPhoneNumber(), guestsActual.get(i).phoneNumber());
        }
    }

    @Test
    void given_guestinrepository_when_fetchbyid_then_returnequalsguest() throws GuestNotFoundException {
        // given
        GuestId idExpected = new GuestId("42");
        Guest guestExpected = Guest.create(
                idExpected,
                new FullName("John", "Doe"),
                Gender.MALE,
                new Address("Hochschulstraße",
                        "1", "Dornbirn",
                        "6850", "Austria"),
                LocalDate.of(1980, 5, 20),
                "+43 660 123 456 789",
                "john.doe@developer.tdd.at",
                0,
                Collections.emptyList()
        );

        Mockito.when(guestRepository.guestById(idExpected)).thenReturn(Optional.of(guestExpected));

        // when
        GuestDetailsDTO guestActual = guestListingService.findGuestById(idExpected.id());

        // then
        assertEquals(guestExpected.getGuestId().id(), guestActual.id());
        assertEquals(guestExpected.getName().firstName(), guestActual.firstName());
        assertEquals(guestExpected.getName().lastName(), guestActual.lastName());
        assertEquals(guestExpected.getGender().name(), guestActual.gender().toUpperCase());
        assertEquals(guestExpected.getAddress().city(), guestActual.city());
        assertEquals(guestExpected.getAddress().country(), guestActual.country());
        assertEquals(guestExpected.getAddress().streetName(), guestActual.streetName());
        assertEquals(guestExpected.getAddress().streetNumber(), guestActual.streetNumber());
        assertEquals(guestExpected.getAddress().zipCode(), guestActual.zipCode());
        assertEquals(guestExpected.getPhoneNumber(), guestActual.phoneNumber());
        assertEquals(guestExpected.getMailAddress(), guestActual.mailAddress());
        assertEquals(guestExpected.getDiscountInPercent(), guestActual.discountInPercent());
        assertEquals(guestExpected.getBirthDate(), guestActual.birthDate());
    }

    @Test
    public void given_missingGuest_when_findById_then_GuestNotFoundExceptionIsThrown() {
        // given
        GuestId guestIdExpected = new GuestId("1");

        Mockito.when(guestRepository.guestById(guestIdExpected)).thenReturn(Optional.empty());

        // when ... then
        Exception exception = assertThrows(GuestNotFoundException.class, () -> {
            guestListingService.findGuestById(guestIdExpected.id());
        });

        String expectedMessage = "Guest with id " + guestIdExpected.id() + " not found";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
