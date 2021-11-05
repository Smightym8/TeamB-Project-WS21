package at.fhv.se.hotel.domain.model.guest;

public class GuestId {
    private final String id;

    public GuestId(String id){
        this.id = id;
    }

    public String id(){
        return this.id;
    }
}
