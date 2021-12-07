package at.fhv.se.hotel.domain.model.guest;

import at.fhv.se.hotel.domain.Generated;
import at.fhv.se.hotel.domain.model.booking.BookingId;

import java.util.Objects;

// TODO: Test
public class Address {
    private String streetName;
    private String streetNumber;
    private String city;
    private String zipCode;
    private String country;

    // Required by hibernate
    private Address(){}

    public Address(String aStreetName, String aStreetNumber, String aCity, String aZipCode, String aCountry) {
        this.streetName = aStreetName;
        this.streetNumber = aStreetNumber;
        this.city = aCity;
        this.zipCode = aZipCode;
        this.country = aCountry;
    }

    public String streetName() {
        return this.streetName;
    }

    public String streetNumber() {
        return this.streetNumber;
    }

    public String city() {
        return this.city;
    }

    public String zipCode() {
        return this.zipCode;
    }

    public String country() {
        return this.country;
    }

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(streetName, address.streetName)
                && Objects.equals(streetNumber, address.streetNumber)
                && Objects.equals(city, address.city)
                && Objects.equals(zipCode, address.zipCode)
                && Objects.equals(country, address.country);
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(streetName, streetNumber, city, zipCode, country);
    }
}
