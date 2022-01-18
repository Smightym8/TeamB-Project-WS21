package at.fhv.se.hotel.application.api.exception;

/**
 * This class represents an exception for the case that there are not enough rooms.
 */
public class NotEnoughRoomsException extends Exception {
    public NotEnoughRoomsException(String message) {
        super(message);
    }
}
