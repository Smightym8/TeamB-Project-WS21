package at.fhv.se.hotel.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

public class BookingDetailsDTO {
    private String id;
    private String guestFirstName;
    private String guestLastName;
    private Map<String, Integer> categoriesWithAmounts;
    private Map<String, BigDecimal> services;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int amountOfAdults;
    private int amountOfChildren;
    private String additionalInformation;

    public static Builder builder() {
        return new Builder();
    }

    public String id() {
        return this.id;
    }

    public String guestFirstName() {
        return guestFirstName;
    }

    public String guestLastName() {
        return guestLastName;
    }

    public Map<String, Integer> categoriesWithAmounts() {
        return this.categoriesWithAmounts;
    }

    public Map<String, BigDecimal> services() {
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
        private final BookingDetailsDTO instance;

        public Builder() {
            this.instance = new BookingDetailsDTO();
        }

        public Builder withId(String id) {
            this.instance.id = id;
            return this;
        }

        public Builder withGuestFirstName(String firstName) {
            this.instance.guestFirstName = firstName;
            return this;
        }

        public Builder withGuestLastName(String lastName) {
            this.instance.guestLastName = lastName;
            return this;
        }

        public Builder withRoomCategoriesAndAmounts(Map<String, Integer> categoriesWithAmounts) {
            this.instance.categoriesWithAmounts = categoriesWithAmounts;
            return this;
        }

        public Builder withServices(Map<String, BigDecimal> services) {
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
            this.instance.amountOfChildren= amountOfChildren;
            return this;
        }

        public Builder withAdditionalInformation(String additionalInformation) {
            this.instance.additionalInformation = additionalInformation;
            return this;
        }

        public BookingDetailsDTO build() {
            Objects.requireNonNull(this.instance.id, "id must be set in BookingDetailsDTO");
            Objects.requireNonNull(this.instance.guestFirstName, "guestFirstName must be set in BookingDetailsDTO");
            Objects.requireNonNull(this.instance.guestLastName, "guestLastName must be set in BookingDetailsDTO");
            Objects.requireNonNull(this.instance.categoriesWithAmounts,
                    "categoriesWithAmounts must be set in BookingDetailsDTO");
            Objects.requireNonNull(this.instance.checkInDate, "checkInDate must be set in BookingDetailsDTO");
            Objects.requireNonNull(this.instance.checkOutDate, "checkOutDate must be set in BookingDetailsDTO");
            
            return this.instance;
        }
    }
}
