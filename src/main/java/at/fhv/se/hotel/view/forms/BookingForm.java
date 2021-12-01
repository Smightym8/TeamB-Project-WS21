package at.fhv.se.hotel.view.forms;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public final class BookingForm {
    private String guestId;
    private List<String> roomCategoryIds;
    private List<String> serviceIds;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate checkInDate;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate checkOutDate;
    private List<Integer> amountsOfRoomCategories;
    private int amountOfAdults;
    private int amountOfChildren;

    // required by spring/thymeleaf
    public BookingForm() {
    }

    public BookingForm(String guestId, List<String> roomCategoryIds, List<String> serviceIds,
                       LocalDate checkInDate, LocalDate checkOutDate, List<Integer> amountsOfRoomCategories,
                       int amountOfAdults, int amountOfChildren) {
        this.guestId = guestId;
        this.roomCategoryIds = roomCategoryIds;
        this.serviceIds = serviceIds;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.amountsOfRoomCategories = amountsOfRoomCategories;
        this.amountOfAdults = amountOfAdults;
        this.amountOfChildren = amountOfChildren;
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

    public List<Integer> getAmountsOfRoomCategories() {
        return amountsOfRoomCategories;
    }

    public void setAmountsOfRoomCategories(List<Integer> amountsOfRoomCategories) {
        this.amountsOfRoomCategories = amountsOfRoomCategories;
    }

    public List<String> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(List<String> serviceIds) {
        this.serviceIds = serviceIds;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getAmountOfAdults() {
        return amountOfAdults;
    }

    public void setAmountOfAdults(int amountOfAdults) {
        this.amountOfAdults = amountOfAdults;
    }

    public int getAmountOfChildren() {
        return amountOfChildren;
    }

    public void setAmountOfChildren(int amountOfChildren) {
        this.amountOfChildren = amountOfChildren;
    }
}
