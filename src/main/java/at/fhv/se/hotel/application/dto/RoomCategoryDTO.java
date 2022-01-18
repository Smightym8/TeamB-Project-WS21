package at.fhv.se.hotel.application.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import org.springframework.boot.jackson.JsonComponent;

import java.util.Objects;

@JsonComponent
public class RoomCategoryDTO {
    private String id;
    private String name;

    public static Builder builder() {
        return new Builder();
    }

    @JsonGetter
    public String id() {
        return this.id;
    }

    @JsonGetter
    public String name() {
        return this.name;
    }

    public static class Builder {
        private final RoomCategoryDTO instance;

        private Builder () {
            this.instance = new RoomCategoryDTO();
        }

        public Builder withId(String id) {
            this.instance.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.instance.name = name;
            return this;
        }

        public RoomCategoryDTO build() {
            Objects.requireNonNull(this.instance.id, "id must be set in RoomCategory");
            Objects.requireNonNull(this.instance.name, "name must be set in RoomCategory");
            return this.instance;
        }
    }
}
