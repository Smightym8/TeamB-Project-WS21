package at.fhv.se.hotel.view.forms;

/**
 * This class represents a form to store the room data that are entered in the UI
 */
public final class RoomForm {
    private String roomName;
    private String categoryName;
    private String roomStatus;

    public RoomForm() {
    }

    public RoomForm(String roomName, String categoryName, String roomStatus) {
        this.roomName = roomName;
        this.categoryName = categoryName;
        this.roomStatus = roomStatus;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }
}
