package at.fhv.se.hotel.application.api.exception;

public class RoomCategoryNotFoundException extends Exception {
    public RoomCategoryNotFoundException(String message) {
        super(message);
    }
}
