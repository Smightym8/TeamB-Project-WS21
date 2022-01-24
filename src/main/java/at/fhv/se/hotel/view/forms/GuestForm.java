package at.fhv.se.hotel.view.forms;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * This class represents a form to store the guest data that are entered in the UI
 */
public final class GuestForm {
    private String guestId;

    @Size(min = 2, max = 30, message = "Firstname has to be between 2 and 30 characters")
    private String firstName;

    @Size(min = 2, max = 30, message = "Lastname has to be between 2 and 30 characters")
    private String lastName;

    @NotEmpty
    @NotNull
    private String gender;

    @Pattern(regexp = "^[a-zA-Z0-9_\\.\\+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-\\.]+$", message = "Please provide a valid email")
    private String eMail;

    @Pattern(regexp = "\\+?\\(?\\d{2,4}\\)?[\\d\\s-]{3,}", message = "Please provide a valid phone-number")
    private String phoneNumber;

    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Past(message = "Must be in the past")
    private LocalDate birthDate;

    @Size(min = 2, max = 30, message = "Street name has to be between 2 and 30 characters")
    private String streetName;

    @Size(min = 1, max = 6, message = "Street number has to be between 1 and 6 characters")
    private String streetNumber;

    @Size(min = 2, max = 8, message = "Zip code has to be between 2 and 8 characters")
    private String zipCode;

    @Size(min = 2, max = 30, message = "City has to be between 2 and 30 characters")
    private String city;

    @Size(min = 5, max = 30, message = "Country has to be between 5 and 30 characters")
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

    public void setGuestId(String guestId) {
        this.guestId = guestId;
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
