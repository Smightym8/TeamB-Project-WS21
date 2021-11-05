package at.fhv.se.hotel.domain.model.guest;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullName fullName = (FullName) o;
        return Objects.equals(firstName, fullName.firstName)
                && Objects.equals(lastName, fullName.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
