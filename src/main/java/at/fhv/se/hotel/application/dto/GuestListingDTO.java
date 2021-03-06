package at.fhv.se.hotel.application.dto;

import java.util.Objects;

public class GuestListingDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String streetName;
    private String streetNumber;
    private String city;
    private String zipCode;
    private String country;
    private String mailAddress;
    private String phoneNumber;

    public static Builder builder() {
        return new Builder();
    }

    public String id() {
        return this.id;
    }

    public String firstName(){
        return this.firstName;
    }

    public String lastName(){
        return this.lastName;
    }

    public String streetName() {return this.streetName;}

    public String streetNumber() {return this.streetNumber;}

    public String city() {return this.city;}

    public String zipCode() {return this.zipCode;}

    public String country() {return this.country;}

    public String mailAddress() {
        return mailAddress;
    }

    public String phoneNumber() {
        return phoneNumber;
    }

    public static class Builder {
        private final GuestListingDTO instance;

        private Builder() {
            this.instance = new GuestListingDTO();
        }

        public Builder withId(String id) {
            this.instance.id = id;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.instance.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.instance.lastName = lastName;
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

        public Builder withCity(String city) {
            this.instance.city = city;
            return this;
        }

        public Builder withZipCode(String zipCode) {
            this.instance.zipCode = zipCode;
            return this;
        }

        public Builder withCountry(String country) {
            this.instance.country = country;
            return this;
        }

        public Builder withMailAddress(String mailAddress) {
            this.instance.mailAddress = mailAddress;
            return this;
        }

        public Builder withPhoneNumber(String phoneNumber) {
            this.instance.phoneNumber = phoneNumber;
            return this;
        }

        public GuestListingDTO build() {
            Objects.requireNonNull(this.instance.id, "id must be set in GuestListingDTO");
            Objects.requireNonNull(this.instance.firstName, "firstName must be set in GuestListingDTO");
            Objects.requireNonNull(this.instance.lastName, "lastName must be set in GuestListingDTO");
            Objects.requireNonNull(this.instance.streetName, "streetName must be set in GuestListingDTO");
            Objects.requireNonNull(this.instance.streetNumber, "streetNumber must be set in GuestListingDTO");
            Objects.requireNonNull(this.instance.city, "city must be set in GuestListingDTO");
            Objects.requireNonNull(this.instance.zipCode, "zipCode must be set in GuestListingDTO");
            Objects.requireNonNull(this.instance.country, "country must be set in GuestListingDTO");
            Objects.requireNonNull(this.instance.mailAddress, "mailAddress must be set in GuestListingDTO");
            Objects.requireNonNull(this.instance.phoneNumber, "phoneNumber must be set in GuestListingDTO");

            return this.instance;
        }
    }
}
