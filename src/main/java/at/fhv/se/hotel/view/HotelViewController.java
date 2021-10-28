package at.fhv.se.hotel.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HotelViewController {
    // Urls
    private static final String MAIN_MENU_URL = "/";

    // Views
    private static final String MAIN_MENU_VIEW = "mainMenu";

    /**
     * This method handles a get request on /.
     * @return MAIN_MENU_VIEW - contains the main menu view.
     */
    @GetMapping(MAIN_MENU_URL)
    public String mainMenu() {
        return MAIN_MENU_VIEW;
    }
}
