package at.fhv.se.hotel.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class InvoiceDTO {
    private String guestFirstName;
    private String guestLastName;
    private String streetName;
    private String streetNumber;
    private String zipCode;
    private String city;
    private Map<String, BigDecimal> services;
    private Map<String, Integer> roomCategories;
    private List<BigDecimal> roomCategoryPrices;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int amountOfNights;
    private BigDecimal localTaxPerPerson;
    private BigDecimal localTaxTotal;
    private BigDecimal valueAddedTaxInPercent;
    private BigDecimal valueAddedTaxInEuro;
    private BigDecimal totalNetAmount;
    private BigDecimal totalGrossAmount;

    public static Builder builder() {
        return new Builder();
    }

    public String guestFirstName() {
        return guestFirstName;
    }

    public String guestLastName() {
        return guestLastName;
    }

    public String streetName() {
        return streetName;
    }

    public String streetNumber() {
        return streetNumber;
    }

    public String zipCode() {
        return zipCode;
    }

    public String city() {
        return city;
    }

    public Map<String, BigDecimal> services() {
        return services;
    }

    public Map<String, Integer> roomCategories() {
        return roomCategories;
    }

    public List<BigDecimal> roomCategoryPrices() {
        return roomCategoryPrices;
    }

    public LocalDate checkInDate() {
        return checkInDate;
    }

    public LocalDate checkOutDate() {
        return checkOutDate;
    }

    public int amountOfNights() {
        return amountOfNights;
    }

    public BigDecimal localTaxPerPerson() {
        return localTaxPerPerson;
    }

    public BigDecimal localTaxTotal() {
        return localTaxTotal;
    }

    public BigDecimal valueAddedTaxInPercent() {
        return valueAddedTaxInPercent;
    }

    public BigDecimal valueAddedTaxInEuro() {
        return valueAddedTaxInEuro;
    }

    public BigDecimal totalNetAmount() {
        return totalNetAmount;
    }

    public BigDecimal totalGrossAmount() {
        return totalGrossAmount;
    }

    public static class Builder {
        private final InvoiceDTO instance;

        private Builder() {
            this.instance = new InvoiceDTO();
        }

        public Builder withGuestFirstName(String guestFirstName) {
            this.instance.guestFirstName = guestFirstName;
            return this;
        }

        public Builder withGuestLastName(String guestLastName) {
            this.instance.guestLastName = guestLastName;
            return this;
        }

        public Builder withStreetName(String streetName) {
            this.instance.streetName = streetName;
            return this;
        }

        public Builder withStreetNumber(String streetNumber) {
            this.instance.streetNumber = streetNumber;
            return this;
        }

        public Builder withZipCode(String zipCode) {
            this.instance.zipCode = zipCode;
            return this;
        }

        public Builder withCity(String city) {
            this.instance.city = city;
            return this;
        }

        public Builder withServices(Map<String, BigDecimal> services) {
            this.instance.services = services;
            return this;
        }

        public Builder withCategories(Map<String, Integer> roomCategories) {
            this.instance.roomCategories = roomCategories;
            return this;
        }

        public Builder withCategoryPrices(List<BigDecimal> roomCategoryPrices) {
            this.instance.roomCategoryPrices = roomCategoryPrices;
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

        public Builder withAmountOfNights(int amountOfNights) {
            this.instance.amountOfNights = amountOfNights;
            return this;
        }

        public Builder withLocalTaxPerPerson(BigDecimal localTaxPerPerson) {
            this.instance.localTaxPerPerson = localTaxPerPerson;
            return this;
        }

        public Builder withLocalTaxTotal(BigDecimal localTaxTotal) {
            this.instance.localTaxTotal = localTaxTotal;
            return this;
        }

        public Builder withValueAddedTaxInPercent(BigDecimal valueAddedTaxInPercent) {
            this.instance.valueAddedTaxInPercent = valueAddedTaxInPercent;
            return this;
        }

        public Builder withValueAddedTaxInEuro(BigDecimal valueAddedTaxInEuro) {
            this.instance.valueAddedTaxInEuro = valueAddedTaxInEuro;
            return this;
        }

        public Builder withTotalNetAmount(BigDecimal totalNetAmount) {
            this.instance.totalNetAmount = totalNetAmount;
            return this;
        }

        public Builder withTotalGrossAmount(BigDecimal totalGrossAmount) {
            this.instance.totalGrossAmount = totalGrossAmount;
            return this;
        }

        public InvoiceDTO build() {
            Objects.requireNonNull(this.instance.guestFirstName, "guestFirstName must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.guestLastName, "guestLastName must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.streetName, "streetName must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.streetNumber, "streetNumber must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.zipCode, "zipCode must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.city, "city must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.roomCategories, "roomCategories must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.roomCategoryPrices, "roomCategoryPrices must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.checkInDate, "checkInDate must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.checkOutDate, "checkOutDate must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.localTaxPerPerson, "localTaxPerPerson must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.localTaxTotal, "localTaxTotal must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.valueAddedTaxInPercent, "valueAddedTaxInPercent must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.valueAddedTaxInEuro, "valueAddedTaxInEuro must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.totalNetAmount, "totalNetAmount must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.totalGrossAmount, "totalGrossAmount must be set in InvoiceDTO");

            return this.instance;
        }
    }
}
