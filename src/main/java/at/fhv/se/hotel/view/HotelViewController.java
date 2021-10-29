package at.fhv.se.hotel.view;

import at.fhv.se.hotel.application.api.BookingListingService;
import at.fhv.se.hotel.application.dto.BookingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HotelViewController {
    // Urls
    private static final String MAIN_MENU_URL = "/";
    private static final String ALL_BOOKINGS_URL = "/booking";

    // Views
    private static final String MAIN_MENU_VIEW = "mainMenu";
    private static final String ALL_BOOKINGS_VIEW = "allBookings";

    // Services
    @Autowired
    private BookingListingService bookingListingService;

    /**
     * This method handles a get request on /.
     * @return MAIN_MENU_VIEW - contains the main menu view.
     */
    @GetMapping(MAIN_MENU_URL)
    public String mainMenu() {
        return MAIN_MENU_VIEW;
    }

    /**
     * This method handles a get request on /booking and passes the bookings to the allBookings view.
     * @param model contains the model to be presented in the view.
     * @return ALL_BOOKINGS_VIEW contains the view with all bookings.
     */
    @GetMapping(ALL_BOOKINGS_URL)
    public String allBookings(Model model) {
        final List<BookingDTO> bookings = bookingListingService.allBookings();

        model.addAttribute(bookings);

        return ALL_BOOKINGS_VIEW;
    }
}
