package at.fhv.se.hotel.smoketests.view;

import at.fhv.se.hotel.view.HotelViewController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

// TODO: Check that all controller are tested
@SpringBootTest
public class ViewControllerSmokeTests {
    @Autowired
    private HotelViewController hotelViewController;

    @Test
    public void given_viewcontrollers_when_injected_then_allnotnull() {
        assertNotNull(hotelViewController);
    }
}
