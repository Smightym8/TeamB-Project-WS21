package at.fhv.se.hotel.view.forms;

import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class GuestForm {
    private String guestId;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String gender;

    @Email
    private String eMail;

    @NotNull
    private String phoneNumber;

    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Past
    private LocalDate birthDate;

    @NotNull
    private String streetName;

    @NotNull
    private String streetNumber;

    @NotNull
    private String zipCode;

    @NotNull
    private String city;

    @NotNull
    private String country;

    @NotNull
    private double discountInPercent;
    
    public GuestForm(){
    }

    public GuestForm(String guestId, String firstName, String lastName, String gender, String eMail,
                     String phoneNumber, LocalDate birthDate, String streetName, String streetNumber,
                     String zipCode, String city, String country, double discountInPercent) {
        this.guestId = guestId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.eMail = eMail;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
        this.discountInPercent = discountInPercent;
    }

    public String getGuestId() {
        return guestId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getDiscountInPercent() {
        return discountInPercent;
    }

    public void setDiscountInPercent(double discountInPercent) {
        this.discountInPercent = discountInPercent;
    }
}
