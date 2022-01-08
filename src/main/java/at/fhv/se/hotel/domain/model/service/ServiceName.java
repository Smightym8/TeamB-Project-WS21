package at.fhv.se.hotel.domain.model.service;

import at.fhv.se.hotel.domain.Generated;

import javax.xml.bind.annotation.XmlElement;
import java.util.Objects;

public class ServiceName {
    private String name;

    // Required by hibernate
    @SuppressWarnings("unused")
    @Generated
    private ServiceName() {
    }

    public ServiceName(String aName){
        this.name = aName;
    }

    public String name() {
        return this.name;
    }

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceName that = (ServiceName) o;
        return Objects.equals(name, that.name);
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
