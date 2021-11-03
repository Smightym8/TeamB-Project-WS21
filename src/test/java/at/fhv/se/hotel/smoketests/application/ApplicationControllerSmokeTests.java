package at.fhv.se.hotel.smoketests.application;

import at.fhv.se.hotel.application.api.BookingListingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ApplicationControllerSmokeTests {
    @Autowired
    BookingListingService bookingListingService;

    @Test
    public void given_applicationcontrollers_when_injected_then_allnotnull() {
        assertNotNull(bookingListingService);
    }
}
