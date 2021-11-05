package at.fhv.se.hotel.domain.model.guest;

public class GuestId {
    private String id;

    public GuestId(String id){
        this.id = id;
    }

    public GuestId() {
    }

    public String id(){
        return this.id;
    }
}
