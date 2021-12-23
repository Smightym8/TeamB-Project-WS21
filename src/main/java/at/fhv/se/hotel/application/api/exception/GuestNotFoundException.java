package at.fhv.se.hotel.application.api.exception;

public class GuestNotFoundException extends Exception {
    public GuestNotFoundException(String message) {
        super(message);
    }
}
