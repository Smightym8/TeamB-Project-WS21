package at.fhv.se.hotel.application.dto;

import java.util.Objects;

public class RoomCategoryDTO {

    private String name;

    public static Builder builder() {
        return new Builder();
    }

    public String name() {
        return this.name;
    }


    private RoomCategoryDTO() {}

    public static class Builder {
        private RoomCategoryDTO instance;

        private Builder () {
            this.instance = new RoomCategoryDTO();
        }

        public Builder withName(String name) {
            this.instance.name = name;
            return this;
        }


        public RoomCategoryDTO build() {
            Objects.requireNonNull(this.instance.name, "name must be set in RoomCategory");
            return this.instance;
        }

    }






}
