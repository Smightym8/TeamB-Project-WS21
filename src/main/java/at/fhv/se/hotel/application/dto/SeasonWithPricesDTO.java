package at.fhv.se.hotel.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class SeasonWithPricesDTO {
    private String seasonName;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<BigDecimal> prices;

    public static Builder builder() {
        return new Builder();
    }

    public String seasonName() {
        return seasonName;
    }

    public LocalDate startDate() {
        return startDate;
    }

    public LocalDate endDate() {
        return endDate;
    }

    public List<BigDecimal> prices() {
        return prices;
    }

    public static class Builder {
        private final SeasonWithPricesDTO instance;

        private Builder() {
            this.instance = new SeasonWithPricesDTO();
        }

        public Builder withSeasonName(String seasonName) {
            this.instance.seasonName = seasonName;
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

        public Builder withPrices(List<BigDecimal> prices) {
            this.instance.prices = prices;
            return this;
        }

        public SeasonWithPricesDTO build() {
            Objects.requireNonNull(this.instance.seasonName, "seasonName must be set in SeasonWithPricesDTO");
            Objects.requireNonNull(this.instance.startDate, "startDate must be set in SeasonWithPricesDTO");
            Objects.requireNonNull(this.instance.endDate, "endDate must be set in SeasonWithPricesDTO");
            Objects.requireNonNull(this.instance.prices, "prices must be set in SeasonWithPricesDTO");

            return this.instance;
        }
    }
}
