package at.fhv.se.hotel.view.forms;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class BookingForm {
    private String firstName;
    private String lastName;
    private String eMail;
    private int tel;

    private Date birthDate;

    private String country;
    private String postalCode;
    private String city;
    private String streetName;
    private String streetNumber;

    //@DateTimeFormat(pattern = "dd/mm/yyyy")

    //private Date bookedFrom;
    private Date bookedUntil;

    public BookingForm() {

    }


    public BookingForm(String fn, String lN, String eM, int telephone, Date bD, String co, String pC, String ci, String sNa, String sNu, Date bU){

        this.firstName = fn;
        this.lastName = lN;
        this.eMail = eM;
        this.tel = telephone;

        this.birthDate = bD;

        this.country = co;
        this.postalCode = pC;
        this.city = ci;
        this.streetName = sNa;
        this.streetNumber = sNu;

        //@DateTimeFormat(pattern = "dd/mm/yyyy")
        //private Date bookedFrom;
        this.bookedUntil = bU;

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

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public Date getBookedUntil() {
        return bookedUntil;
    }

    public void setBookedUntil(Date bookedUntil) {
        this.bookedUntil = bookedUntil;
    }
}