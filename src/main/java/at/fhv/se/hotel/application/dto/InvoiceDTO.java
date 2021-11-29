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
    private BigDecimal invoiceAmount;
    private BigDecimal invoiceTaxes;
    private BigDecimal invoiceTotalAmount;
    private LocalDate dueByDate;

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

    public BigDecimal invoiceAmount() {
        return invoiceAmount;
    }

    public BigDecimal invoiceTaxes() {
        return invoiceTaxes;
    }

    public BigDecimal invoiceTotalAmount() {
        return invoiceTotalAmount;
    }

    public LocalDate dueByDate() {
        return dueByDate;
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

        public Builder withInvoiceAmount(BigDecimal invoiceAmount) {
            this.instance.invoiceAmount = invoiceAmount;
            return this;
        }

        public Builder withInvoiceTaxes(BigDecimal invoiceTaxes) {
            this.instance.invoiceTaxes = invoiceTaxes;
            return this;
        }

        public Builder withTotalAmount(BigDecimal invoiceTotalAmount) {
            this.instance.invoiceTotalAmount = invoiceTotalAmount;
            return this;
        }

        public Builder withDueByDate(LocalDate dueByDate) {
            this.instance.dueByDate = dueByDate;
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
            Objects.requireNonNull(this.instance.invoiceAmount, "invoiceAmount must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.invoiceTaxes, "invoiceTaxes must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.invoiceTotalAmount, "invoiceTotalAmount must be set in InvoiceDTO");
            Objects.requireNonNull(this.instance.dueByDate, "dueByDate must be set in InvoiceDTO");

            return this.instance;
        }
    }
}
