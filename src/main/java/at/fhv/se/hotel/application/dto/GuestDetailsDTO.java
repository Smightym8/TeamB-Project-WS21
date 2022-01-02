package at.fhv.se.hotel.application.dto;

import java.time.LocalDate;
import java.util.Objects;

public class GuestDetailsDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String gender;
    private String streetName;
    private String streetNumber;
    private String city;
    private String zipCode;
    private String country;
    private String mailAddress;
    private String phoneNumber;
    private LocalDate birthDate;
    private double discountInPercent;

    public static Builder builder() {
        return new Builder();
    }

    public String id() {
        return id;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public String gender() {
        return gender;
    }

    public String streetName() {
        return streetName;
    }

    public String streetNumber() {
        return streetNumber;
    }

    public String city() {
        return city;
    }

    public String zipCode() {
        return zipCode;
    }

    public String country() {
        return country;
    }

    public String mailAddress() {
        return mailAddress;
    }

    public String phoneNumber() {
        return phoneNumber;
    }

    public LocalDate birthDate() {
        return birthDate;
    }

    public double discountInPercent() {
        return discountInPercent;
    }

    public static class Builder {
        private final GuestDetailsDTO instance;

        private Builder() {
            this.instance = new GuestDetailsDTO();
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

        public Builder withGender(String gender) {
            this.instance.gender = gender;
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

        public Builder withBirthDate(LocalDate birthDate) {
            this.instance.birthDate = birthDate;
            return this;
        }

        public Builder withDiscount(double discount) {
            this.instance.discountInPercent = discount;
            return this;
        }

        public GuestDetailsDTO build() {
            Objects.requireNonNull(this.instance.id, "id must be set in GuestDetailsDTO");
            Objects.requireNonNull(this.instance.firstName, "firstName must be set in GuestDetailsDTO");
            Objects.requireNonNull(this.instance.lastName, "lastName must be set in GuestDetailsDTO");
            Objects.requireNonNull(this.instance.gender, "gender must be set in GuestDetailsDTO");
            Objects.requireNonNull(this.instance.streetName, "streetName must be set in GuestDetailsDTO");
            Objects.requireNonNull(this.instance.streetNumber, "streetNumber must be set in GuestDetailsDTO");
            Objects.requireNonNull(this.instance.city, "city must be set in GuestDetailsDTO");
            Objects.requireNonNull(this.instance.zipCode, "zipCode must be set in GuestDetailsDTO");
            Objects.requireNonNull(this.instance.country, "country must be set in GuestDetailsDTO");
            Objects.requireNonNull(this.instance.mailAddress, "mailAddress must be set in GuestDetailsDTO");
            Objects.requireNonNull(this.instance.phoneNumber, "phoneNumber must be set in GuestDetailsDTO");
            Objects.requireNonNull(this.instance.birthDate, "birthDate must be set in GuestDetailsDTO");

            return this.instance;
        }
    }
}
