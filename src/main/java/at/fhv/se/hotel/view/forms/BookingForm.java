package at.fhv.se.hotel.view.forms;

public final class BookingForm {
    private String guestId;
    private String firstName;
    private String lastName;
    private String roomCategory;

    // required by spring/thymeleaf
    public BookingForm() {
    }

    public BookingForm(String guestId, String firstName, String lastName, String roomCategory) {
        this.guestId = guestId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roomCategory = roomCategory;
    }

    public void addGuest(String guestId, String firstName, String lastName) {
        this.guestId = guestId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRoomCategory() {
        return roomCategory;
    }

    public void setRoomCategory(String roomCategory) {
        this.roomCategory = roomCategory;
    }
}
