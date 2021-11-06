package at.fhv.se.hotel.view.forms;

public final class BookingForm {
    private String guestId;
    private String roomCategoryId;
    private String serviceId;

    // required by spring/thymeleaf
    public BookingForm() {
    }

    public BookingForm(String guestId, String roomCategoryId, String serviceId) {
        this.guestId = guestId;
        this.roomCategoryId = roomCategoryId;
        this.serviceId = serviceId;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public String getRoomCategoryId() {
        return roomCategoryId;
    }

    public void setRoomCategoryId(String roomCategoryId) {
        this.roomCategoryId = roomCategoryId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
