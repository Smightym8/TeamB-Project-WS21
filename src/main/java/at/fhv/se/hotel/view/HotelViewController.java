package at.fhv.se.hotel.view;

import at.fhv.se.hotel.application.api.RoomCategoryListingService;
import at.fhv.se.hotel.application.dto.RoomCategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HotelViewController {
    // Urls
    private static final String MAIN_MENU_URL = "/";
    private static final String CHOOSE_CATEGORY_URL = "/choosecategory";


    // Views
    private static final String MAIN_MENU_VIEW = "mainMenu";
    private static final String CHOOSE_CATEGORY_VIEW = "choosecategory";

    @Autowired
    private RoomCategoryListingService roomCategoryListingService;

    /**
     * This method handles a get request on /.
     * @return MAIN_MENU_VIEW - contains the main menu view.
     */
    @GetMapping(MAIN_MENU_URL)
    public String mainMenu() {
        return MAIN_MENU_VIEW;
    }


    @GetMapping(CHOOSE_CATEGORY_URL)
    public String allRoomCategories(Model model) {
        final List<RoomCategoryDTO> categories = roomCategoryListingService.allRoomCategories();

        model.addAttribute("categories", categories);

        return CHOOSE_CATEGORY_VIEW;
    }
}
