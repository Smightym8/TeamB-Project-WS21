package at.fhv.se.hotel.domain.model.guest;

public class FullName {
    private String firstName;
    private String lastName;

    public FullName(String aFirstName, String aLastName){
        this.firstName = aFirstName;
        this.lastName = aLastName;
    }

    public String firstName() {
        return this.firstName;
    }

    public String lastName() {
        return this.lastName;
    }
}
