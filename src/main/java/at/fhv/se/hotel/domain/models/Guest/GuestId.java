package at.fhv.se.hotel.domain.models.Guest;

public class GuestId {
    private String id;

    public GuestId(String id){
        this.id = id;
    }

    public String id(){
        return this.id;
    }
}
