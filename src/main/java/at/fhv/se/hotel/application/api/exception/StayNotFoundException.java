package at.fhv.se.hotel.application.api.exception;

public class StayNotFoundException extends Exception {
    public StayNotFoundException() {
    }

    public StayNotFoundException(String message) {
        super(message);
    }
}
