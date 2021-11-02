package at.fhv.se.hotel.application.dto;

import java.time.LocalDate;
import java.util.Objects;

public class GuestDTO {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    public static Builder builder() {
        return new Builder();
    }

    public String firstName(){
        return this.firstName;
    }

    public String lastName(){
        return this.lastName;
    }

    public LocalDate birthDate(){
        return this.birthDate;
    }

    public static class Builder {
        private final GuestDTO instance;

        private Builder() {
            this.instance = new GuestDTO();
        }

        public Builder withFirstName(String firstName) {
            this.instance.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.instance.lastName = lastName;
            return this;
        }

        public Builder withBirthDate(LocalDate birthDate) {
            this.instance.birthDate = birthDate;
            return this;
        }

        public GuestDTO build() {
            Objects.requireNonNull(this.instance.firstName, "firstName must be set in GuestDTO");
            Objects.requireNonNull(this.instance.lastName, "lastName must be set in GuestDTO");
            Objects.requireNonNull(this.instance.birthDate, "birthDate must be set in GuestDTO");

            return this.instance;
        }
    }
}
