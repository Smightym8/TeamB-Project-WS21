package at.fhv.se.hotel.domain.model.roomcategory;

public class RoomCategoryId {
    private String id;

    // Required by hibernate
    public RoomCategoryId() {
    }

    public RoomCategoryId(String id){
        this.id = id;
    }

    public String id(){
        return this.id;
    }
}
