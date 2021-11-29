package at.fhv.se.hotel.api;

import at.fhv.se.hotel.application.api.BookingDetailsService;
import at.fhv.se.hotel.application.api.BookingListingService;
import at.fhv.se.hotel.application.api.StayListingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class ViewApiTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookingListingService bookingListingService;

    @MockBean
    StayListingService stayListingService;


    @Test
    public void when_rooturl_then_statusok_and_mainMenuView_called() throws Exception {
        this.mockMvc.perform(get("/").accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("mainMenu"));
    }

    @Test
    public void when_bookinglisturl_then_statusok_and_allBookingsView_and_bookingListingService_called() throws Exception {
        // when ... then
        this.mockMvc.perform(get("/bookinglist").accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("allBookings"));

        // then
        Mockito.verify(bookingListingService, times(1)).allBookings();
    }

    @Test
    public void when_staylisturl_then_statusok_and_allStaysView_and_stayListingService_called() throws Exception {
        // when ... then
        this.mockMvc.perform(get("/staylist").accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("allStays"));

        // then
        Mockito.verify(stayListingService, times(1)).allStays();
    }
    public void when_startbookingurl_then_statusok_and_createBookingView_called() throws Exception {
        // when ... then
        this.mockMvc.perform(get("/booking").accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("startCreateBooking"));
    }

    @Test
    public void when_guestformurl_then_statusok_and_createGuestView_called() throws Exception {
        // when ... then
        this.mockMvc.perform(get("/guestform").accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("createGuest"));
    }

    @Test
    public void when_choosedatesurl_then_statusok_and_chooseBookingDatesView_called() throws Exception {
        // when ... then
        this.mockMvc.perform(get("/choosedates").accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("chooseBookingDates"));
    }

    @Test
    public void when_errorurl_then_statusok_and_errorView_called_and_message_displayed() throws Exception {
        // when ... then
        this.mockMvc.perform(get("/displayerror")
                .param("message", "testMessage")
                .accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("errorView"));
    }

    // TODO: Implement test for post mappings

    // TODO: Implement test for mappings with path variables
}
