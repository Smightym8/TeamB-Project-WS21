package at.fhv.se.hotel.integration.application;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class BookingCreationServiceTests {
    @Test
    public void given_bookingFormData_when_book_then_bookingWithMatchingDetailsCreated() {

    }
}
