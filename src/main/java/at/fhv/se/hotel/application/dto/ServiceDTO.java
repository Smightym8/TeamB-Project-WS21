package at.fhv.se.hotel.application.dto;

import java.util.Objects;

public class ServiceDTO {

    private String id;
    private String name;

    public static Builder builder() {
        return new Builder();
    }

    public String id() {
        return this.id;
    }

    public String name() {
        return this.name;
    }

    private ServiceDTO() {}

    public static class Builder {
        private final ServiceDTO instance;

        private Builder() {
            this.instance = new ServiceDTO();
        }

        public Builder withId(String id) {
            this.instance.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.instance.name = name;
            return this;
        }

        public ServiceDTO build() {
            Objects.requireNonNull(this.instance.id, "id must be set in ServiceDTO");
            Objects.requireNonNull(this.instance.name, "name must be set in ServiceDTO");

            return this.instance;
        }
    }

}
