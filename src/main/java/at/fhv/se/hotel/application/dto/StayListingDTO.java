package at.fhv.se.hotel.application.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

//Todo: only unpaid rooms
public class StayListingDTO {
    private String id;
    private String guestFirstName;
    private String guestLastName;
    private LocalDate checkOutDate;
    private List<String> rooms;
    private int amountOfPersons;
    private boolean isActive;

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

    public List<String> rooms() {
        return rooms;
    }

    public int amountOfPersons() {
        return amountOfPersons;
    }

    public boolean isActive() {
        return isActive;
    }

    public static class Builder {
        private final StayListingDTO instance;

        private Builder() {
            this.instance = new StayListingDTO();
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

        public Builder withRooms(List<String> rooms) {
            this.instance.rooms = rooms;
            return this;
        }

        public Builder withAmountOfPersons(int amountOfPersons) {
            this.instance.amountOfPersons = amountOfPersons;
            return this;
        }

        public Builder withIsActive(boolean isActive) {
            this.instance.isActive = isActive;
            return this;
        }

        public StayListingDTO build() {
            Objects.requireNonNull(this.instance.id, "id must be set in StayListingDTO");
            Objects.requireNonNull(this.instance.guestFirstName, "guestFirstName must be set in StayListingDTO");
            Objects.requireNonNull(this.instance.guestLastName, "guestLastName must be set in StayListingDTO");
            Objects.requireNonNull(this.instance.checkOutDate, "checkOutDate must be set in StayListingDTO");
            Objects.requireNonNull(this.instance.rooms, "rooms must be set in StayListingDTO");

            return this.instance;
        }
    }
}
