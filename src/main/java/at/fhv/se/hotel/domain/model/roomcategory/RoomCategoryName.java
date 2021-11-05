package at.fhv.se.hotel.domain.model.roomcategory;

public class RoomCategoryName {
    private String name;

    // Required by hibernate
    public RoomCategoryName() {
    }

    public RoomCategoryName(String aName){
        this.name = aName;
    }

    public String name() {
        return this.name;
    }

}
