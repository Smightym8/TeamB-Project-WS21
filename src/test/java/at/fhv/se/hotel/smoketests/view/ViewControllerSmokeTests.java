package at.fhv.se.hotel.smoketests.view;

import at.fhv.se.hotel.view.HotelRestController;
import at.fhv.se.hotel.view.HotelViewController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ViewControllerSmokeTests {
    @Autowired
    private HotelViewController hotelViewController;

    @Autowired
    private HotelRestController hotelRestController;

    @Test
    public void given_viewcontrollers_when_injected_then_allnotnull() {
        assertNotNull(hotelViewController);
        assertNotNull(hotelRestController);
    }

}
