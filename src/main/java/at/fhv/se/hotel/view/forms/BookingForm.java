package at.fhv.se.hotel.view.forms;

import java.util.List;

public final class BookingForm {
    private String guestId;
    private List<String> roomCategoryIds;
    private List<String> serviceIds;

    // required by spring/thymeleaf
    public BookingForm() {
    }

    public BookingForm(String guestId, List<String> roomCategoryIds, List<String> serviceIds) {
        this.guestId = guestId;
        this.roomCategoryIds = roomCategoryIds;
        this.serviceIds = serviceIds;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public List<String> getRoomCategoryIds() {
        return roomCategoryIds;
    }

    public void setRoomCategoryIds(List<String> roomCategoryIds) {
        this.roomCategoryIds = roomCategoryIds;
    }

    public List<String> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(List<String> serviceIds) {
        this.serviceIds = serviceIds;
    }
}
