package at.fhv.se.hotel.application.dto;

import java.util.Objects;

public class RoomDTO {
    private String name;

    public static Builder builder() {
        return new Builder();
    }

    public String name() {
        return this.name;
    }

    public static class Builder {
        private final RoomDTO instance;

        private Builder() {
            this.instance = new RoomDTO();
        }

        public Builder withName(String name) {
            this.instance.name = name;
            return this;
        }

        public RoomDTO build() {
            Objects.requireNonNull(this.instance.name, "name must be set in GuestDTO");

            return this.instance;
        }
    }
}
