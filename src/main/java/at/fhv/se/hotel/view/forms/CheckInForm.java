package at.fhv.se.hotel.view.forms;

import java.util.List;

public class CheckInForm {
    private String bookingId;
    private List<String> roomNames;

    public CheckInForm() {
    }

    public CheckInForm(String bookingId, List<String> roomNames) {
        this.bookingId = bookingId;
        this.roomNames = roomNames;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public List<String> getRoomNames() {
        return roomNames;
    }

    public void setRoomNames(List<String> roomNames) {
        this.roomNames = roomNames;
    }
}
