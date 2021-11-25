package at.fhv.se.hotel.application.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RoomCategoryDTO {
    private String id;
    private String name;
    private Map<String, BigDecimal> seasonalPrices;

    public static Builder builder() {
        return new Builder();
    }

    public String id() {
        return this.id;
    }
    public String name() {
        return this.name;
    }
    public Map<String, BigDecimal> prices() {
        return this.seasonalPrices;
    }

    private RoomCategoryDTO() {}

    public static class Builder {
        private RoomCategoryDTO instance;

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

        public Builder withPrices(Map<String, BigDecimal> seasonalPrices) {
            this.instance.seasonalPrices = seasonalPrices;
            return this;
        }

        public RoomCategoryDTO build() {
            Objects.requireNonNull(this.instance.id, "id must be set in RoomCategory");
            Objects.requireNonNull(this.instance.name, "name must be set in RoomCategory");
            Objects.requireNonNull(this.instance.seasonalPrices, "prices must be set in RoomCategory");
            return this.instance;
        }

    }






}
