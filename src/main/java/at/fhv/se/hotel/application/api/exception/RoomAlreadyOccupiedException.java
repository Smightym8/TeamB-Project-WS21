package at.fhv.se.hotel.application.api.exception;

/**
 * This class represents an exception for the case that a room is already occupied.
 */
public class RoomAlreadyOccupiedException extends Exception {
    public RoomAlreadyOccupiedException(String message) {
        super(message);
    }
}
