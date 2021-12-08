package at.fhv.se.hotel.application.dto;

import java.util.Objects;

public class InvoiceListingDTO {
    // TODO: Add status (isPaid) to domain model and then here
    private String id;
    private String invoiceNumber;
    private String guestFirstName;
    private String guestLastName;
    private String streetName;
    private String streetNumber;
    private String zipCode;
    private String city;
    private String totalGrossAmount;

    public static Builder builder() {
        return new Builder();
    }

    public String id() {
        return id;
    }

    public String invoiceNumber() {
        return invoiceNumber;
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

    public String amount() {
        return totalGrossAmount;
    }

    public static class Builder {
        private final InvoiceListingDTO instance;

        private Builder() {
            this.instance = new InvoiceListingDTO();
        }

        private Builder withId(String id) {
            this.instance.id = id;
            return this;
        }

        private Builder withInvoiceNumber(String invoiceNumber) {
            this.instance.invoiceNumber = invoiceNumber;
            return this;
        }

        private Builder withGuestFirstName(String guestFirstName) {
            this.instance.guestFirstName = guestFirstName;
            return this;
        }

        private Builder withGuestLastName(String guestLastName) {
            this.instance.guestLastName = guestLastName;
            return this;
        }

        private Builder withStreetName(String streetName) {
            this.instance.streetName = streetName;
            return this;
        }

        private Builder withStreetNumber(String streetNumber) {
            this.instance.streetNumber = streetNumber;
            return this;
        }

        private Builder withZipCode(String zipCode) {
            this.instance.zipCode = zipCode;
            return this;
        }

        private Builder withCity(String city) {
            this.instance.city =city;
            return this;
        }

        private Builder withTotalGrossAmountAmount(String totalGrossAmountAmount) {
            this.instance.totalGrossAmount = totalGrossAmountAmount;
            return this;
        }

        public InvoiceListingDTO build() {
            Objects.requireNonNull(this.instance.id, "id must be set in InvoiceListingDTO");
            Objects.requireNonNull(this.instance.invoiceNumber, "invoiceNumber must be set in InvoiceListingDTO");
            Objects.requireNonNull(this.instance.guestFirstName, "guestFirstName must be set in InvoiceListingDTO");
            Objects.requireNonNull(this.instance.guestLastName, "guestLastName must be set in InvoiceListingDTO");
            Objects.requireNonNull(this.instance.streetName, "streetName must be set in InvoiceListingDTO");
            Objects.requireNonNull(this.instance.streetNumber, "streetNumber must be set in InvoiceListingDTO");
            Objects.requireNonNull(this.instance.zipCode, "zipCode must be set in InvoiceListingDTO");
            Objects.requireNonNull(this.instance.city, "city must be set in InvoiceListingDTO");
            Objects.requireNonNull(this.instance.totalGrossAmount, "totalGrossAmount must be set in InvoiceListingDTO");

            return this.instance;
        }
    }
}
