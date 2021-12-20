package at.fhv.se.hotel.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StayDetailsDTO {
    private String id;
    private String guestFirstName;
    private String guestLastName;
    private Map<String, String> roomsWithCategories;
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
        return id;
    }

    public String guestFirstName() {
        return guestFirstName;
    }

    public String guestLastName() {
        return guestLastName;
    }

    public Map<String, String> roomsWithCategories() {
        return roomsWithCategories;
    }

    public Map<String, BigDecimal> services() {
        return services;
    }

    public LocalDate checkInDate() {
        return checkInDate;
    }

    public LocalDate checkOutDate() {
        return checkOutDate;
    }

    public int amountOfAdults() {
        return amountOfAdults;
    }

    public int amountOfChildren() {
        return amountOfChildren;
    }

    public String additionalInformation() {
        return additionalInformation;
    }

    public static class Builder {
        private final StayDetailsDTO instance;

        public Builder() {
            this.instance = new StayDetailsDTO();
        }

        public Builder withId(String id) {
            this.instance.id = id;
            return this;
        }

        public Builder withGuestFirstName(String guestFirstName) {
            this.instance.guestFirstName = guestFirstName;
            return this;
        }

        public Builder withGuestLastName(String guestLastName) {
            this.instance.guestLastName = guestLastName;
            return this;
        }

        public Builder withRoomsWithCategories(Map<String, String> roomsWithCategories) {
            this.instance.roomsWithCategories = roomsWithCategories;
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

        public StayDetailsDTO build() {
            Objects.requireNonNull(this.instance.id, "id must be set in StayDetailsDTO");
            Objects.requireNonNull(this.instance.guestFirstName, "guestFirstName must be set in StayDetailsDTO");
            Objects.requireNonNull(this.instance.guestLastName, "guestLastName must be set in StayDetailsDTO");
            Objects.requireNonNull(this.instance.roomsWithCategories, "roomsWithCategories must be set in StayDetailsDTO");
            Objects.requireNonNull(this.instance.checkInDate, "checkInDate must be set in StayDetailsDTO");
            Objects.requireNonNull(this.instance.checkOutDate, "checkOutDate must be set in StayDetailsDTO");

            return this.instance;
        }
    }
}
