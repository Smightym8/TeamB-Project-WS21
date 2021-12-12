package at.fhv.se.hotel.application.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

// TODO: Remove nestings (e.g. String guestFirstName, ...)
public class BookingSummaryDTO {
    private GuestDTO guest;
    private Map<RoomCategoryDTO, Integer> categoriesWithAmounts;
    private List<ServiceDTO> services;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int amountOfAdults;
    private int amountOfChildren;
    private String additionalInformation;

    public static Builder builder() {
        return new Builder();
    }

    public GuestDTO guest() {
        return this.guest;
    }

    public Map<RoomCategoryDTO, Integer> categoriesWithAmounts() {
        return this.categoriesWithAmounts;
    }

    public List<ServiceDTO> services() {
        return this.services;
    }

    public LocalDate checkInDate() {
        return this.checkInDate;
    }

    public LocalDate checkOutDate() {
        return this.checkOutDate;
    }

    public int amountOfAdults() {
        return amountOfAdults;
    }

    public int amountOfChildren() {
        return amountOfChildren;
    }

    public String additionalInformation() { return additionalInformation; };

    public static class Builder {
        private final BookingSummaryDTO instance;

        public Builder() {
            this.instance = new BookingSummaryDTO();
        }

        public Builder withGuest(GuestDTO guest) {
            this.instance.guest = guest;
            return this;
        }

        public Builder withRoomCategoriesAndAmounts(Map<RoomCategoryDTO, Integer> categoriesWithAmounts) {
            this.instance.categoriesWithAmounts = categoriesWithAmounts;
            return this;
        }

        public Builder withServices(List<ServiceDTO> services) {
            this.instance.services = services;
            return this;
        }

        public Builder withCheckInDate(LocalDate checkInDate) {
            this.instance.checkInDate = checkInDate;
            return this;
        }

        public Builder withCheckOutDate(LocalDate checkOutDate) {
            this.instance.checkOutDate = checkOutDate;
            return this;
        }

        public Builder withAmountOfAdults(int amountOfAdults) {
            this.instance.amountOfAdults = amountOfAdults;
            return this;
        }

        public Builder withAmountOfChildren(int amountOfChildren) {
            this.instance.amountOfChildren = amountOfChildren;
            return this;
        }

        public Builder withAdditionalInformation(String additionalInformation) {
            this.instance.additionalInformation = additionalInformation;
            return this;
        }

        public BookingSummaryDTO build() {
            Objects.requireNonNull(this.instance.guest, "guest must be set in BookingSummaryDTO");
            Objects.requireNonNull(this.instance.categoriesWithAmounts,
                    "categoriesWithAmounts must be set in BookingSummaryDTO");
            Objects.requireNonNull(this.instance.checkInDate, "checkInDate must be set in BookingSummaryDTO");
            Objects.requireNonNull(this.instance.checkOutDate, "checkOutDate must be set in BookingSummaryDTO");

            return this.instance;
        }
    }
}
