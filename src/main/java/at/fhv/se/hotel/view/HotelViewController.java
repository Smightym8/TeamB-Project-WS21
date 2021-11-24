package at.fhv.se.hotel.view;

import at.fhv.se.hotel.application.api.*;
import at.fhv.se.hotel.application.dto.*;
import at.fhv.se.hotel.view.forms.BookingForm;
import at.fhv.se.hotel.view.forms.GuestForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class HotelViewController {

    private static final String HOME_URL = "/";
    private static final String HOME_VIEW = "home";

    private static final String ROOMS_URL = "/rooms";
    private static final String ROOMS_VIEW = "rooms";

    private static final String GUESTS_URL = "/guests";
    private static final String GUESTS_VIEW = "guests";

    private static final String BOOKINGS_URL = "/bookings";
    private static final String BOOKINGS_VIEW = "bookings";

    private static final String STAYS_URL = "/stays";
    private static final String STAYS_VIEW = "stays";

    private static final String INVOICES_URL = "/invoices";
    private static final String INVOICES_VIEW = "invoices";

    /*---*/

    private static final String CREATE_GUEST_URL = "/createguest";
    private static final String CREATE_GUEST_VIEW = "createguest";

    /*---*/

    private static final String BOOKING_SUMMARY_URL = "/bookingSummary";
    private static final String CHOOSE_CATEGORY_URL = "/choosecategory";
    private static final String CHOOSE_GUEST_URL = "/chooseguest";
    private static final String CHOOSE_SERVICE_URL = "/chooseservice";
    private static final String CHOOSE_DATES_URL = "/choosedates";
    private static final String CREATE_BOOKING_URL = "/createbooking";
    private static final String ERROR_URL = "/displayerror";
    private static final String SHOW_BOOKING_DETAILS_URL = "/booking/details/{id}";
    private static final String GUEST_FORM_URL = "/guestform";
    private static final String ASSIGNED_ROOMS_URL = "/assignedRooms";

    // Views

    private static final String BOOKING_SUMMARY_VIEW = "bookingSummary";
    private static final String BOOKING_DETAILS_VIEW = "bookingDetails";
    private static final String CHOOSE_CATEGORY_VIEW = "chooseCategory";
    private static final String CHOOSE_GUEST_VIEW = "chooseGuest";
    private static final String CHOOSE_SERVICE_VIEW = "chooseService";
    private static final String CHOOSE_DATES_VIEW = "chooseBookingDates";
    private static final String ERROR_VIEW = "errorView";
    private static final String ASSIGNED_ROOMS_VIEW = "assignedRooms";



    @Autowired
    private BookingListingService bookingListingService;

    @Autowired
    private RoomCategoryListingService roomCategoryListingService;

    @Autowired
    private GuestListingService guestListingService;

    @Autowired
    private ServiceListingService serviceListingService;

    @Autowired
    private BookingSummaryService bookingSummaryService;

    @Autowired
    private BookingCreationService bookingCreationService;

    @Autowired
    private BookingDetailsService bookingDetailsService;

    @Autowired
    private GuestCreationService guestCreationService;

    @Autowired
    private CheckInService checkInService;

    /*---------*/

//  ---Home---
    @GetMapping(HOME_URL)
    public String home(Model model) {
        final List<BookingDTO> bookings = bookingListingService.allBookings();

        model.addAttribute("bookings", bookings);

        return HOME_VIEW;
    }

//  ---Rooms---
    @GetMapping(ROOMS_URL)
    public String rooms(Model model) {

        return ROOMS_VIEW;
    }

//  ---Guests---
    @GetMapping(GUESTS_URL)
    public String guests(Model model) {
        final List<GuestDTO> guests = guestListingService.allGuests();

        model.addAttribute("guests", guests);

        return GUESTS_VIEW;
    }

//  ---Bookings---
    @GetMapping(BOOKINGS_URL)
    public String bookings(Model model) {
        final List<BookingDTO> bookings = bookingListingService.allBookings();

        model.addAttribute("bookings", bookings);

        return BOOKINGS_VIEW;
    }

//  ---Stays---
    @GetMapping(STAYS_URL)
    public String stays(Model model) {

        return STAYS_VIEW;
    }

//  ---Invoices---
    @GetMapping(INVOICES_URL)
    public String invoices(Model model) {

        return INVOICES_VIEW;
    }

    /*-----------*/






    @GetMapping(CREATE_GUEST_URL)
    public String createGuestForm(Model model) {
        GuestForm guestForm = new GuestForm();

        model.addAttribute("guest", guestForm);

        return CREATE_GUEST_VIEW;
    }

    @PostMapping(CREATE_GUEST_URL)
    public String createGuestSave(@ModelAttribute("guest") @Valid GuestForm guestForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return CREATE_GUEST_VIEW;
        }

        guestCreationService.createGuest(
                guestForm.getFirstName(),
                guestForm.getLastName(),
                guestForm.getGender(),
                guestForm.geteMail(),
                guestForm.getPhoneNumber(),
                guestForm.getBirthDate(),
                guestForm.getStreetName(),
                guestForm.getStreetNumber(),
                guestForm.getZipCode(),
                guestForm.getCity(),
                guestForm.getCountry()
        );

        return "redirect:" + GUESTS_URL;
    }


    @PostMapping(CHOOSE_CATEGORY_URL)
    public String chooseRoomCategories(@ModelAttribute("form") BookingForm form, Model model) {
        final List<RoomCategoryDTO> categories = roomCategoryListingService.allRoomCategories();

        model.addAttribute("categories", categories);
        model.addAttribute("form", form);

        return CHOOSE_CATEGORY_VIEW;
    }

    @PostMapping(CHOOSE_GUEST_URL)
    public String chooseGuestForBooking(@ModelAttribute("form") BookingForm bookingForm, Model model) {
        List<GuestDTO> guests = guestListingService.allGuests();

        model.addAttribute("guests", guests);
        model.addAttribute("form", bookingForm);

        return CHOOSE_GUEST_VIEW;
    }

    @PostMapping(CHOOSE_SERVICE_URL)
    public String chooseServices(@ModelAttribute("form") BookingForm form, Model model) {
        final List<ServiceDTO> services = serviceListingService.allServices();

        model.addAttribute("services", services);
        model.addAttribute("form", form);

        return CHOOSE_SERVICE_VIEW;
    }

    @GetMapping(CHOOSE_DATES_URL)
    public String chooseCheckInCheckOutDate(Model model) {

        BookingForm form = new BookingForm();

        model.addAttribute("form", form);

        return CHOOSE_DATES_VIEW;
    }

    @PostMapping(BOOKING_SUMMARY_URL)   //at the end of a booking
    public String showSummary(@ModelAttribute("form") BookingForm form,
                              @RequestParam("isCreated") boolean isCreated,
                              Model model) {

        BookingSummaryDTO bookingSummaryDTO = bookingSummaryService.createSummary(
                form.getGuestId(),
                form.getRoomCategoryIds(),
                form.getAmounts(),
                form.getServiceIds(),
                form.getCheckInDate(),
                form.getCheckOutDate()
        );

        model.addAttribute("bookingSummary", bookingSummaryDTO);
        model.addAttribute("form", form);
        model.addAttribute("isCreated", isCreated);

        return BOOKING_SUMMARY_VIEW;
    }

    @GetMapping(SHOW_BOOKING_DETAILS_URL)   //for check-in
    public String showBookingDetails(@PathVariable String id,
                              Model model) {

        BookingDetailsDTO bookingDetailsDTO =  bookingDetailsService.detailsByBookingId(id);
        model.addAttribute("bookingDetails", bookingDetailsDTO);

        return BOOKING_DETAILS_VIEW;
    }

    @GetMapping(ASSIGNED_ROOMS_URL)
    public String assignedRooms(@RequestParam("bookingId") String bookingId,
                                @RequestParam("isCheckedIn") boolean isCheckedIn,
                                Model model) {

        List<RoomDTO> assignedRooms = checkInService.assignRooms(bookingId);

        if(isCheckedIn) {
            checkInService.checkIn(bookingId, assignedRooms);
        }

        model.addAttribute("bookingId", bookingId);
        model.addAttribute("assignedRooms", assignedRooms);
        model.addAttribute("isCheckedIn", isCheckedIn);

        return ASSIGNED_ROOMS_VIEW;
    }

    @PostMapping(CREATE_BOOKING_URL)
    public String createBooking(@ModelAttribute("form") BookingForm form,
                                Model model){
        bookingCreationService.book(form.getGuestId(),
                form.getRoomCategoryIds(),
                form.getAmounts(),
                form.getServiceIds(),
                form.getCheckInDate(),
                form.getCheckOutDate());
        return showSummary(form, true, model);
    }

    @GetMapping(ERROR_URL)
    public String displayError(@RequestParam("message") String message, Model model){
        model.addAttribute("message", message);
        return ERROR_VIEW;
    }

    private static ModelAndView redirectError(String message){
        return new ModelAndView("redirect:" + ERROR_URL + "?message" + message);
    }
}
