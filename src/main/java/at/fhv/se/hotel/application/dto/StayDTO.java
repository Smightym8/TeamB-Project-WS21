package at.fhv.se.hotel.application.dto;

import java.time.LocalDate;
import java.util.Objects;

public class StayDTO {
    private String id;
    private String guestFirstName;
    private String guestLastName;
    private LocalDate checkOutDate;

    public static Builder builder(){
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


    public LocalDate checkOutDate() {
        return checkOutDate;
    }

    public static class Builder{
        private final StayDTO instance;

        private Builder() {
            this.instance = new StayDTO();
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

        public Builder withCheckOutDate(LocalDate checkOutDate) {
            this.instance.checkOutDate = checkOutDate;
            return this;

        }

        public StayDTO build() {
            Objects.requireNonNull(this.instance.id, "id must be set in StayDTO");
            Objects.requireNonNull(this.instance.guestFirstName, "guestFirstName must be set in StayDTO");
            Objects.requireNonNull(this.instance.guestLastName, "guestLastName must be set in StayDTO");
            Objects.requireNonNull(this.instance.checkOutDate, "checkOutDate must be set in StayDTO");

            return this.instance;
        }
    }
}
