package at.fhv.se.hotel.domain.models.Guest;

public class Address {
    private String streetName;
    private String streetNumber;
    private String city;
    private String zipCode;
    private String country;

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
}
