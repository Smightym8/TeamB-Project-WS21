package at.fhv.se.hotel.view;

import at.fhv.se.hotel.application.api.GuestListingService;
import at.fhv.se.hotel.application.api.RoomCategoryListingService;
import at.fhv.se.hotel.application.dto.BookingDTO;
import at.fhv.se.hotel.application.dto.GuestDTO;
import at.fhv.se.hotel.application.dto.RoomCategoryDTO;
import at.fhv.se.hotel.view.forms.BookingForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.awt.print.Book;
import java.util.Collections;
import java.util.List;

@Controller
public class HotelViewController {
    // Urls
    private static final String MAIN_MENU_URL = "/";
    private static final String CREATE_BOOKING_URL = "/booking";
    private static final String CHOOSE_CATEGORY_URL = "/choosecategory";
    private static final String CHOOSE_GUEST_URL = "/chooseguest";

    private static final String ADD_GUEST_TO_BOOKING_URL = "/booking/addGuest";
    private static final String ADD_CATEGORY_TO_BOOKING_URL = "/booking/addCategory";

    // Views
    private static final String MAIN_MENU_VIEW = "mainMenu";
    private static final String CREATE_BOOKING_VIEW = "createBooking";
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

    @GetMapping(CREATE_BOOKING_URL)
    public String createBooking (Model model) {
        return CREATE_BOOKING_VIEW;
    }

    @GetMapping(CHOOSE_CATEGORY_URL)
    public String allRoomCategories(Model model) {
        final List<RoomCategoryDTO> categories = roomCategoryListingService.allRoomCategories();

        model.addAttribute("categories", categories);

        return CHOOSE_CATEGORY_VIEW;
    }

    @GetMapping(CHOOSE_GUEST_URL)
    public String chooseGuestForBooking(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam (value = "name", required = false) String name,
            Model model) {
        List<GuestDTO> guests = guestListingService.allGuests();

        if((id != null) && (!id.isEmpty())) {
            guests = Collections.singletonList(guestListingService.findGuestById(id));
        } else if((name != null) && (!name.isEmpty())) {
            guests = guestListingService.findGuestByName(name);
        }

        model.addAttribute("guests", guests);

        return CHOOSE_GUEST_VIEW;
    }

    @GetMapping(ADD_GUEST_TO_BOOKING_URL)
    public ModelAndView addGuestToBooking(
            @ModelAttribute BookingForm form,
            @RequestParam(value = "guestId") String guestId,
            @RequestParam(value = "firstName") String firstName,
            @RequestParam(value = "lastName") String lastName) {

        form.addGuest(guestId, firstName, lastName);
        return redirectToCreateBooking(form);
    }

    @GetMapping(ADD_CATEGORY_TO_BOOKING_URL)
    public ModelAndView addRoomCategoryToBooking(
            @ModelAttribute BookingForm form,
            @RequestParam(value = "roomCategory") String roomCategory) {

        form.setRoomCategory(roomCategory);
        return redirectToCreateBooking(form);
    }

    private static ModelAndView redirectToCreateBooking(final BookingForm form) {
        return new ModelAndView("redirect:" +
                CREATE_BOOKING_URL +
                "?guestId=" +
                form.getGuestId() +
                "&firstName=" +
                form.getFirstName() +
                "&lastName=" +
                form.getLastName() +
                "&roomCategory=" +
                form.getRoomCategory()
        );
    }
}
