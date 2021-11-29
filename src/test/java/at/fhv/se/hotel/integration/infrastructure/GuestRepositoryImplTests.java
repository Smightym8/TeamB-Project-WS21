package at.fhv.se.hotel.integration.infrastructure;

import at.fhv.se.hotel.domain.model.guest.*;
import at.fhv.se.hotel.domain.repository.GuestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
