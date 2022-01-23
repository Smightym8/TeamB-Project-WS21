package at.fhv.se.hotel.view.forms;

import java.util.List;

/**
 * This class represents a form to store the invoice data that are entered in the UI
 */
public final class InvoiceForm {
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
