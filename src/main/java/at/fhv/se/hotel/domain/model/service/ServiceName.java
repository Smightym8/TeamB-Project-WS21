package at.fhv.se.hotel.domain.model.service;

public class ServiceName {
    private String name;

    public ServiceName(String aName){
        this.name = aName;
    }

    public String name() {
        return this.name;
    }
}
