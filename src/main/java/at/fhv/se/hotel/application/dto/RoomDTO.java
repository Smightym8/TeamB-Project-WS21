package at.fhv.se.hotel.application.dto;

import at.fhv.se.hotel.domain.Generated;

import java.util.Objects;

public class RoomDTO {
    private String name;
    private String categoryName;
    private String roomStatus;

    public static Builder builder() {
        return new Builder();
    }

    public String name() {
        return this.name;
    }

    public String categoryName() {
        return this.categoryName;
    }

    public String roomStatus() {
        return roomStatus;
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

        public Builder withCategory(String categoryName) {
            this.instance.categoryName = categoryName;
            return this;
        }

        public Builder withStatus(String roomStatus) {
            this.instance.roomStatus = roomStatus;
            return this;
        }

        public RoomDTO build() {
            Objects.requireNonNull(this.instance.name, "name must be set in RoomDTO");
            Objects.requireNonNull(this.instance.categoryName, "categoryName must be set in RoomDTO");
            Objects.requireNonNull(this.instance.roomStatus, "roomStatus must be set in RoomDTO");

            return this.instance;
        }
    }

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomDTO roomDTO = (RoomDTO) o;
        return Objects.equals(name, roomDTO.name) && Objects.equals(categoryName, roomDTO.categoryName) && Objects.equals(roomStatus, roomDTO.roomStatus);
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(name, categoryName, roomStatus);
    }
}
