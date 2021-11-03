package at.fhv.se.hotel.view;

import at.fhv.se.hotel.application.api.GuestListingService;
import at.fhv.se.hotel.application.api.RoomCategoryListingService;
import at.fhv.se.hotel.application.dto.GuestDTO;
import at.fhv.se.hotel.application.dto.RoomCategoryDTO;
import at.fhv.se.hotel.view.forms.BookingForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
public class HotelViewController {
    // Urls
    private static final String MAIN_MENU_URL = "/";
    private static final String CHOOSE_CATEGORY_URL = "/choosecategory";
    private static final String CHOOSE_GUEST_URL = "/chooseguest";

    // Views
    private static final String MAIN_MENU_VIEW = "mainMenu";
    private static final String CHOOSE_CATEGORY_VIEW = "chooseCategory";
    private static final String CHOOSE_GUEST_VIEW = "chooseGuest";



    // Services
    @Autowired
    private RoomCategoryListingService roomCategoryListingService;

    @Autowired
    private GuestListingService guestListingService;

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

    @GetMapping(CHOOSE_GUEST_URL)
    public String chooseGuestForBooking(Model model,@RequestParam(value = "id", required = false) String id,
                                        @RequestParam (value = "name", required = false) String name) {
        List<GuestDTO> guests = guestListingService.allGuests();

        if((id != null) && (!id.isEmpty())) {
            guests = Collections.singletonList(guestListingService.findGuestById(id));
        } else if((name != null) && (!name.isEmpty())) {
            guests = guestListingService.findGuestByName(name);
        }

        model.addAttribute("guests", guests);

        return CHOOSE_GUEST_VIEW;
    }

}
