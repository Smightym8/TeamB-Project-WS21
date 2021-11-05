package at.fhv.se.hotel.domain.model.service;

import java.util.Objects;

public class ServiceId {
    private String id;

    // Required by hibernate
    public ServiceId() {
    }

    public ServiceId(String id){
        this.id = id;
    }

    public String id(){
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceId serviceId = (ServiceId) o;
        return Objects.equals(id, serviceId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
