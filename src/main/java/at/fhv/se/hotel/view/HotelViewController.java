package at.fhv.se.hotel.view;

import at.fhv.se.hotel.application.api.*;
import at.fhv.se.hotel.application.dto.BookingDTO;
import at.fhv.se.hotel.application.dto.GuestDTO;
import at.fhv.se.hotel.application.dto.RoomCategoryDTO;
import at.fhv.se.hotel.application.dto.ServiceDTO;
import at.fhv.se.hotel.view.forms.BookingForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HotelViewController {
    // Urls
    private static final String MAIN_MENU_URL = "/";
    private static final String BOOKING_FORM_URL = "/booking";
    private static final String ALL_BOOKINGS_URL = "/bookinglist";
    private static final String CHOOSE_CATEGORY_URL = "/choosecategory";
    private static final String CHOOSE_GUEST_URL = "/chooseguest";
    private static final String CHOOSE_SERVICE_URL = "/chooseservice";

    private static final String CREATE_BOOKING_URL = "/createBooking";

    // Views
    private static final String MAIN_MENU_VIEW = "mainMenu";
    private static final String ALL_BOOKINGS_VIEW = "allBookings";
    private static final String CREATE_BOOKING_VIEW = "createBooking";
    private static final String CHOOSE_CATEGORY_VIEW = "chooseCategory";
    private static final String CHOOSE_GUEST_VIEW = "chooseGuest";
    private static final String CHOOSE_SERVICE_VIEW = "chooseService";

    // Services
    @Autowired
    private BookingListingService bookingListingService;

    @Autowired
    private RoomCategoryListingService roomCategoryListingService;

    @Autowired
    private GuestListingService guestListingService;

    @Autowired
    private ServiceListingService serviceListingService;

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

        model.addAttribute("bookings", bookings);

        return ALL_BOOKINGS_VIEW;
    }

    @GetMapping(BOOKING_FORM_URL)
    public String createBookingForm(Model model) {
        List<GuestDTO> guests = guestListingService.allGuests();
        List<RoomCategoryDTO> roomCategories = roomCategoryListingService.allRoomCategories();
        List<ServiceDTO> services = serviceListingService.allServices();

        BookingForm bookingForm = new BookingForm();

        model.addAttribute("guests", guests);
        model.addAttribute("roomCategories", roomCategories);
        model.addAttribute("services", services);
        model.addAttribute("form", bookingForm);

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

        model.addAttribute("guests", guests);

        return CHOOSE_GUEST_VIEW;
    }

    @GetMapping(CHOOSE_SERVICE_URL)
    public String allServices(Model model) {
        final List<ServiceDTO> services = serviceListingService.allServices();

        model.addAttribute("services", services);

        return CHOOSE_SERVICE_VIEW;
    }

    @PostMapping(CREATE_BOOKING_URL)
    public String createBooking(@ModelAttribute("form") BookingForm form) {
        System.out.println("GuestId: " + form.getGuestId() + "\n" +
                "RoomCategoryId: " + form.getRoomCategoryId() + "\n" +
                "ServiceId: " + form.getServiceId());

        return redirectBookingForm();
    }

    private static String redirectBookingForm() {
        return "redirect:" + BOOKING_FORM_URL;
    }
}
