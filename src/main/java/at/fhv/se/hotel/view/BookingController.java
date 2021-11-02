package at.fhv.se.hotel.view;

import at.fhv.se.hotel.view.forms.BookingForm;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;

import java.util.Date;

@Controller
public class BookingController {

    private String firstName;
    private String lastName;
    private String eMail;
    private int tel;

    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private Date birthdate;

    private String country;
    private String postalCode;
    private String city;
    private String streetName;
    private String streetNumber;

    //@DateTimeFormat(pattern = "dd/mm/yyyy")
    //private Date bookedFrom;
    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private Date bookedUntil;

    final BookingForm form = new BookingForm(firstName, lastName, eMail, tel, birthdate, country, postalCode, city, streetName, streetNumber, bookedUntil);

//    model.addAttribute("form", form);

}
/*
* package at.fhv.se.banking.view;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import at.fhv.se.banking.application.dto.AccountDTO;
import at.fhv.se.banking.application.dto.AccountDetailsDTO;
import at.fhv.se.banking.application.dto.CustomerDTO;
import at.fhv.se.banking.application.dto.CustomerDetailsDTO;
import at.fhv.se.banking.application.dto.TXLineDTO;
import at.fhv.se.banking.view.forms.AccountForm;

@Controller
public class BankingViewController {

    private static final String ALL_CUSTOMERS_URL = "/";
    private static final String CUSTOMER_URL = "/customer";
    private static final String ACCOUNT_URL = "/account";
    private static final String ERROR_URL = "/displayerror";

    private static final String POST_DEPOSIT_URL = "/account/deposit";
    private static final String POST_WITHDRAW_URL = "/account/withdraw";
    private static final String POST_TRANSFER_URL = "/account/transfer";

    private static final String ALL_CUSTOMERS_VIEW = "allCustomers";
    private static final String CUSTOMER_VIEW = "customer";
    private static final String ACCOUNT_VIEW = "account";
    private static final String ERROR_VIEW = "errorView";

    // TODO: inject Application Service Interfaces

    @GetMapping(ALL_CUSTOMERS_URL)
    public String allCustomers(Model model) {
        // TODO: make a call to an Application Service to get all Customers
        // TODO: this is fake test data, remove when implementing
        final List<CustomerDTO> customers = Arrays.asList(
            CustomerDTO.builder()
                .withName("Max Mustermann")
                .withId("1")
                .build()
        );
        model.addAttribute("customers", customers);

        return ALL_CUSTOMERS_VIEW;
    }

    @GetMapping(CUSTOMER_URL)
    public ModelAndView customer(
            @RequestParam("id") String customerId,
            Model model) {

        // TODO: make a call to an Application Service to get the customer details for customer with customerId
        // TODO: redirect to the error page in case of an error situation - use redirectError("SOME MESSAGE"); for that
        // TODO: this is fake test data, remove when implementing
        final CustomerDetailsDTO customerDetails = CustomerDetailsDTO.builder()
            .withCustomer(CustomerDTO.builder()
                .withName("Max Mustermann")
                .withId("1")
                .build())
            .addAccount(AccountDetailsDTO.builder()
                .withBalance(1000)
                .withIban("AT12 12345 01234567890")
                .build())
            .build();
        model.addAttribute("customer", customerDetails);
        return new ModelAndView(CUSTOMER_VIEW);
    }

    @GetMapping(ACCOUNT_URL)
    public ModelAndView accountInfo(
            @RequestParam("iban") String iban,
            @RequestParam("id") String customerId,
            @RequestParam("name") String customerName,
            Model model) {

        // TODO: make a call to an Application Service to get the Account with iban
        // TODO: redirect to the error page in case of an error situation - use redirectError("SOME MESSAGE"); for that
        // TODO: this is fake test data, remove when implementing
        final AccountDTO account = AccountDTO.builder()
            .withDetails(AccountDetailsDTO.builder()
                .withBalance(1000)
                .withIban("AT12 12345 01234567890")
                .build())
            .addTXLine(TXLineDTO.builder()
                .ofAmount(100)
                .withIban("AT98 54321 09876543210")
                .withName("Maria Musterfrau")
                .atTime(LocalDateTime.now())
                .withReference("Miete")
                .build())
            .build();

        final AccountForm form = new AccountForm(customerId, customerName, iban);

        model.addAttribute("account", account);
        model.addAttribute("form", form);
        return new ModelAndView(ACCOUNT_VIEW);
    }

    @PostMapping(POST_DEPOSIT_URL)
    public ModelAndView depositAccount(
            @ModelAttribute AccountForm form,
            Model model) {

        // TODO: make a call to an Application Service to deposit form.getAmount() into the Account with form.getIban()
        // TODO: redirect to the error page in case of an error situation - use redirectError("SOME MESSAGE"); for that

        return redirectToAccount(form);
    }

    @PostMapping(POST_WITHDRAW_URL)
    public ModelAndView withdrawAccount(
            @ModelAttribute AccountForm form,
            Model model) {

        // TODO: make a call to an Application Service to withdraw form.getAmount() into the Account with form.getIban()
        // TODO: redirect to the error page in case of an error situation - use redirectError("SOME MESSAGE"); for that

        return redirectToAccount(form);
    }

    @PostMapping(POST_TRANSFER_URL)
    public ModelAndView transferAccount(@ModelAttribute AccountForm form,
            @RequestParam("receivingIban") String receivingIban,
            @RequestParam("reference") String reference,
            Model model) {

        // TODO: make a call to an Application Service to transfer form.getAmount() from an account with form.getIban() to an account with
        //       receivingIban with a specific reference
        // TODO: redirect to the error page in case of an error situation - use redirectError("SOME MESSAGE"); for that

        return redirectToAccount(form);
    }

    @GetMapping(ERROR_URL)
    public String displayError(@RequestParam("msg") String msg, Model model) {
        model.addAttribute("msg", msg);
        return ERROR_VIEW;
    }

    // NOTE: used to redirect to an error page displaying an error message
    @SuppressWarnings("unused")
    private static ModelAndView redirectError(String msg) {
        return new ModelAndView("redirect:" + ERROR_URL + "?msg=" + msg);
    }

    private static ModelAndView redirectToAccount(final AccountForm form) {
        return new ModelAndView("redirect:" +
            ACCOUNT_URL +
            "?iban=" +
            form.getIban() +
            "&id=" +
            form.getCustomerId() +
            "&name=" +
            form.getCustomerName());
    }
}

* */