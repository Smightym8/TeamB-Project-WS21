package at.fhv.se.hotel.domain.model.service;

public class ServiceId {
    private String id;

    public ServiceId(String id){
        this.id = id;
    }

    public String id(){
        return this.id;
    }
}
