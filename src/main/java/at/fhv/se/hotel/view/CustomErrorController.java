package at.fhv.se.hotel.view;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

// Handles the Errors which do not get caught by Exceptions and displays a custom Page instead of a Whitelabel

@Controller
public class CustomErrorController implements ErrorController {

    /*----- Undefined Error -----*/
    private static final String UNDEFINED_ERROR_URL = "/error";
    private static final String UNDEFINED_ERROR_VIEW = "errorView";

    @RequestMapping(UNDEFINED_ERROR_URL)
    public String displayStandardError(Model model) {
        model.addAttribute("message", "Unexpected error. Please try again or try contacting a developer");

        return UNDEFINED_ERROR_VIEW;
    }
}
