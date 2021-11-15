package at.fhv.se.hotel.view.forms;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class GuestForm {
    private String firstName;
    private String lastName;
    private String gender;
    private String eMail;
    private String phoneNumber;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate birthDate;

    private String streetName;
    private String streetNumber;
    private String zipCode;
    private String city;
    private String country;
    
    public GuestForm(){
    }

    public GuestForm(String firstName, String lastName, String gender, String eMail, 
                     String phoneNumber, LocalDate birthDate, String streetName, 
                     String streetNumber, String zipCode, String city, String country){

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
    }
    
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String geteMail() {
        return eMail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}
