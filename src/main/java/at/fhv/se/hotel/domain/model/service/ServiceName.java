package at.fhv.se.hotel.domain.model.service;

public class ServiceName {
    private String name;

    // Required by hibernate
    public ServiceName() {
    }

    public ServiceName(String aName){
        this.name = aName;
    }

    public String name() {
        return this.name;
    }
}
