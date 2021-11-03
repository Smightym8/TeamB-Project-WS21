package at.fhv.se.hotel.application.dto;

import java.time.LocalDate;
import java.util.Objects;

/**
 * This class represents a Booking that will be displayed with the id, roomCategory, startDate and endDate.
 */
public class BookingDTO {
    private String id;
    private String roomCategory;
    private LocalDate startDate;
    private LocalDate endDate;

    public static Builder builder() {
        return new Builder();
    }

    public String id() {
        return this.id;
    }

    public String roomCategory() {
        return this.roomCategory;
    }

    public LocalDate startDate() {
        return this.startDate;
    }

    public LocalDate endDate() {
        return this.endDate;
    }

    private BookingDTO() {}

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

        public Builder withStartDate(LocalDate startDate) {
            this.instance.startDate = startDate;
            return this;
        }

        public Builder withEndDate(LocalDate endDate) {
            this.instance.endDate = endDate;
            return this;
        }

        public BookingDTO build() {
            Objects.requireNonNull(this.instance.id, "id must be set in BookingDTO");
            Objects.requireNonNull(this.instance.roomCategory, "roomCategory must be set in BookingDTO");
            Objects.requireNonNull(this.instance.startDate, "startDate must be set in BookingDTO");
            Objects.requireNonNull(this.instance.endDate, "endDate must be set in BookingDTO");

            return this.instance;
        }
    }
}
