package at.fhv.se.hotel.application.api.exception;

public class NotEnoughRoomsException extends Exception {
    public NotEnoughRoomsException(String message) {
        super(message);
    }
}
