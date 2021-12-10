package at.fhv.se.hotel.api;

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
    public void when_rooturl_then_statusok_and_homeView_and_allBookings_and_allStays_called() throws Exception {
        this.mockMvc.perform(get("/").accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("sidebar/home"));

        // then
        Mockito.verify(bookingListingService, times(1)).allBookings();
        Mockito.verify(stayListingService, times(1)).allStays();
    }

    @Test
    public void when_bookingsurl_then_statusok_and_bookingsView_and_bookingListingService_called() throws Exception {
        // when ... then
        this.mockMvc.perform(get("/bookings").accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("sidebar/bookings"));

        // then
        Mockito.verify(bookingListingService, times(1)).allBookings();
    }

    @Test
    public void when_staysurl_then_statusok_and_staysView_and_stayListingService_called() throws Exception {
        // when ... then
        this.mockMvc.perform(get("/stays").accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("sidebar/stays"));

        // then
        Mockito.verify(stayListingService, times(1)).allStays();
    }

    @Test
    public void when_createguesturl_then_statusok_and_createGuestView_called() throws Exception {
        // when ... then
        this.mockMvc.perform(get("/createguest").accept(org.springframework.http.MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("createGuest"));
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
