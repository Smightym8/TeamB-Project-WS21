package at.fhv.se.hotel.application.dto;

import java.time.LocalDate;
import java.util.Objects;

/**
 * This class represents a Booking that will be displayed with the id, roomCategory, startDate and endDate.
 */
public class BookingDTO {
    private String id;
    private String roomCategory;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public static Builder builder() {
        return new Builder();
    }

    public String id() {
        return this.id;
    }

    public String roomCategory() {
        return this.roomCategory;
    }

    public LocalDate checkInDate() {
        return this.checkInDate;
    }

    public LocalDate checkOutDate() {
        return this.checkOutDate;
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

        public Builder withRoomCategory(String roomCategory) {
            this.instance.roomCategory = roomCategory;
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

        public BookingDTO build() {
            Objects.requireNonNull(this.instance.id, "id must be set in BookingDTO");
            Objects.requireNonNull(this.instance.roomCategory, "roomCategory must be set in BookingDTO");
            Objects.requireNonNull(this.instance.checkInDate, "checkInDate must be set in BookingDTO");
            Objects.requireNonNull(this.instance.checkOutDate, "checkOutDate must be set in BookingDTO");

            return this.instance;
        }
    }
}
