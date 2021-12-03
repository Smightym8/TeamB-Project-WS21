package at.fhv.se.hotel.smoketests.application;

import at.fhv.se.hotel.application.api.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ApplicationControllerSmokeTests {
    @Autowired
    BookingCreationService bookingCreationService;

    @Autowired
    BookingListingService bookingListingService;

    @Autowired
    BookingSummaryService bookingSummaryService;

    @Autowired
    CheckInService checkInService;

    @Autowired
    GuestCreationService guestCreationService;

    @Autowired
    GuestListingService guestListingService;

    @Autowired
    RoomCategoryListingService roomCategoryListingService;

    @Autowired
    ServiceListingService serviceListingService;

    @Test
    public void given_applicationcontrollers_when_injected_then_allnotnull() {
        assertNotNull(bookingCreationService);
        assertNotNull(bookingListingService);
        assertNotNull(bookingSummaryService);
        assertNotNull(checkInService);
        assertNotNull(guestCreationService);
        assertNotNull(guestListingService);
        assertNotNull(roomCategoryListingService);
        assertNotNull(serviceListingService);
    }
}
