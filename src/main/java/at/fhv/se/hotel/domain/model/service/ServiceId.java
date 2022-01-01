package at.fhv.se.hotel.domain.model.service;

import at.fhv.se.hotel.domain.Generated;

import java.util.Objects;

// TODO: Test
public class ServiceId {
    private String id;

    // Required by hibernate
    @SuppressWarnings("unused")
    private ServiceId() {
    }

    public ServiceId(String id){
        this.id = id;
    }

    public String id(){
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
