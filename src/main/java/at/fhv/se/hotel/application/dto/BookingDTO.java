package at.fhv.se.hotel.application.dto;

import java.time.LocalDate;
import java.util.Objects;

/**
 * This class represents a Booking that will be displayed with the id, roomCategory, startDate and endDate.
 */
public class BookingDTO {
    private String id;
    // TODO: Split up into guestFirstName and guestLastName --> change BookingListingServiceTests
    private String guestName;
    private LocalDate checkInDate;
    private boolean isActive;

    public static Builder builder() {
        return new Builder();
    }

    public String id() {
        return this.id;
    }

    public String guestName() {
        return this.guestName;
    }

    public LocalDate checkInDate() {
        return this.checkInDate;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public static class Builder {
        private final BookingDTO instance;

        private Builder() {
            this.instance = new BookingDTO();
        }

        public Builder withId(String id) {
            this.instance.id = id;
            return this;
        }

        public Builder withGuestName(String guestName) {
            this.instance.guestName = guestName;
            return this;
        }

        public Builder withCheckInDate(LocalDate checkInDate) {
            this.instance.checkInDate = checkInDate;
            return this;
        }

        public Builder withStatus(boolean isActive) {
            this.instance.isActive = isActive;
            return this;
        }

        public BookingDTO build() {
            Objects.requireNonNull(this.instance.id, "id must be set in BookingDTO");
            Objects.requireNonNull(this.instance.guestName, "guestName must be set in BookingDTO");
            Objects.requireNonNull(this.instance.checkInDate, "checkInDate must be set in BookingDTO");

            return this.instance;
        }
    }
}
