package at.fhv.se.hotel.domain.model.guest;

import at.fhv.se.hotel.domain.Generated;

import javax.xml.bind.annotation.XmlElement;
import java.util.Objects;

// TODO: Test
public class FullName {
    private String firstName;
    private String lastName;

    // Required by hibernate
    @SuppressWarnings("unused")
    @Generated
    private FullName(){}

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

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullName fullName = (FullName) o;
        return Objects.equals(firstName, fullName.firstName)
                && Objects.equals(lastName, fullName.lastName);
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
