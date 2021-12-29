package at.fhv.se.hotel.application.dto;

import java.time.LocalDate;
import java.util.Objects;

/**
 * This class represents a Booking that will be displayed with the id, roomCategory, startDate and endDate.
 */
public class BookingListingDTO {
    private String id;
    private String guestFirstName;
    private String guestLastName;
    private String streetName;
    private String streetNumber;
    private String zipCode;
    private String city;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private boolean isActive;

    public static Builder builder() {
        return new Builder();
    }

    public String id() {
        return this.id;
    }

    public String guestFirstName() {
        return this.guestFirstName;
    }

    public String guestLastName() {
        return guestLastName;
    }

    public String streetName() {
        return streetName;
    }

    public String streetNumber() {
        return streetNumber;
    }

    public String zipCode() {
        return zipCode;
    }

    public String city() {
        return city;
    }

    public LocalDate checkInDate() {
        return this.checkInDate;
    }

    public LocalDate checkOutDate() {
        return this.checkOutDate;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public static class Builder {
        private final BookingListingDTO instance;

        private Builder() {
            this.instance = new BookingListingDTO();
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

        public Builder withStreetName(String streetName) {
            this.instance.streetName = streetName;
            return this;
        }

        public Builder withStreetNumber(String streetNumber) {
            this.instance.streetNumber = streetNumber;
            return this;
        }

        public Builder withZipCode(String zipCode) {
            this.instance.zipCode = zipCode;
            return this;
        }

        public Builder withCity(String city) {
            this.instance.city = city;
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

        public Builder withStatus(boolean isActive) {
            this.instance.isActive = isActive;
            return this;
        }

        public BookingListingDTO build() {
            Objects.requireNonNull(this.instance.id, "id must be set in BookingDTO");
            Objects.requireNonNull(this.instance.guestFirstName, "guestFirstName must be set in BookingDTO");
            Objects.requireNonNull(this.instance.guestLastName, "guestLastName must be set in BookingDTO");
            Objects.requireNonNull(this.instance.checkInDate, "checkInDate must be set in BookingDTO");
            Objects.requireNonNull(this.instance.checkOutDate, "checkOutDate must be set in BookingDTO");

            return this.instance;
        }
    }
}
