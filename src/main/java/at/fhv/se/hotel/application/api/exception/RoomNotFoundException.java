package at.fhv.se.hotel.application.api.exception;

public class RoomNotFoundException extends Exception {
    public RoomNotFoundException(String message) {
        super(message);
    }
}
