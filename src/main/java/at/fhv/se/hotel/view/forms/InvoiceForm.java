package at.fhv.se.hotel.view.forms;

import java.util.List;

public class InvoiceForm {
    List<String> roomNames;

    public InvoiceForm(){
    }

    public List<String> getRoomNames() {
        return roomNames;
    }

    public void setRoomNames(List<String> roomNames) {
        this.roomNames = roomNames;
    }
}
