package at.fhv.se.hotel.integration.infrastructure;

import at.fhv.se.hotel.domain.model.guest.*;
import at.fhv.se.hotel.domain.repository.GuestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("GuestRepoTest")
@SpringBootTest
@Transactional
public class GuestRepositoryImplTests {
    @Autowired
    GuestRepository guestRepository;

    @Autowired
    private EntityManager em;

    @Test
    void given_guest_when_addguesttorepository_then_returnequalsguest() {
        // given
        GuestId idExpected = new GuestId("42");
        Guest guestExpected = Guest.create(
                idExpected,
                new FullName("John", "Doe"),
                Gender.MALE,
                new Address("Street", "1", "Dornbirn", "6850", "Austria"),
                LocalDate.of(1990, 3, 1),
                "+43 660 123 456 789",
                "john.doe@gmail.com",
                Collections.emptyList()
        );

        // when
        guestRepository.add(guestExpected);
        em.flush();
        Guest guestActual = guestRepository.guestById(idExpected).get();

        // then
        assertEquals(guestExpected, guestActual);
        assertEquals(idExpected, guestActual.getGuestId());
    }

    @Test
    void given_3guests_when_addgueststorepository_then_return3equalsguests() {
        // given
        GuestId idExpected1 = new GuestId("42");
        Guest guestExpected1 = Guest.create(
                idExpected1,
                new FullName("John", "Doe"),
                Gender.MALE,
                new Address("Street", "1", "Dornbirn", "6850", "Austria"),
                LocalDate.of(1990, 3, 1),
                "+43 660 123 456 789",
                "john.doe@gmail.com",
                Collections.emptyList()
        );

        GuestId idExpected2 = new GuestId("203");
        Guest guestExpected2 = Guest.create(
                idExpected2,
                new FullName("Ali", "Cinar"),
                Gender.MALE,
                new Address("Hochschulstraße",
                        "1", "Dornbirn",
                        "6850", "Austria"),
                LocalDate.of(1997, 8, 27),
                "+43 676 123 456 789",
                "ali.cinar@students.fhv.at",
                Collections.emptyList()
        );

        GuestId idExpected3 = new GuestId("1337");
        Guest guestExpected3 = Guest.create(
                idExpected3,
                new FullName("Michael", "Spiegel"),
                Gender.MALE,
                new Address("Hochschulstraße",
                        "1", "Dornbirn",
                        "6850", "Austria"),
                LocalDate.of(1999, 3, 20),
                "+43 660 123 456 789",
                "michael.spiegel@students.fhv.at",
                Collections.emptyList()
        );

        List<Guest> guestsExpected = Arrays.asList(
                guestExpected1,
                guestExpected2,
                guestExpected3
        );

        // when
        guestRepository.add(guestExpected1);
        guestRepository.add(guestExpected2);
        guestRepository.add(guestExpected3);
        em.flush();
        List<Guest> guestsActual = guestRepository.findAllGuests();

        // then
        assertEquals(guestsExpected.size(), guestsActual.size());
        for (int i = 0; i < 3; i++) {
            assertEquals(guestsExpected.get(i).getGuestId(), guestsActual.get(i).getGuestId());
            assertEquals(guestsExpected.get(i).getName(), guestsActual.get(i).getName());
            assertEquals(guestsExpected.get(i).getGender(), guestsActual.get(i).getGender());
            assertEquals(guestsExpected.get(i).getAddress(), guestsActual.get(i).getAddress());
            assertEquals(guestsExpected.get(i).getBirthDate(), guestsActual.get(i).getBirthDate());
            assertEquals(guestsExpected.get(i).getMailAddress(), guestsActual.get(i).getMailAddress());
            assertEquals(guestsExpected.get(i).getPhoneNumber(), guestsActual.get(i).getPhoneNumber());
            assertEquals(guestsExpected.get(i).getBookings(), guestsActual.get(i).getBookings());
        }
    }
}
