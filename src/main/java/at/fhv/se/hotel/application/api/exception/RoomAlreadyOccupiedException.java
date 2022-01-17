package at.fhv.se.hotel.application.api.exception;

public class RoomAlreadyOccupiedException extends Exception {
    public RoomAlreadyOccupiedException(String message) {
        super(message);
    }
}
