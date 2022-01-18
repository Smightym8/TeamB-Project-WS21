package at.fhv.se.hotel.domain.model.service;

import at.fhv.se.hotel.domain.Generated;

import java.util.Objects;

/**
 * This class is a value object for the service which contains the id
 */
public class ServiceId {
    private String id;

    // Required by hibernate
    @SuppressWarnings("unused")
    @Generated
    private ServiceId() {
    }

    public ServiceId(String id) {
        this.id = id;
    }

    public String id() {
        return this.id;
    }

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceId serviceId = (ServiceId) o;
        return Objects.equals(id, serviceId.id);
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
