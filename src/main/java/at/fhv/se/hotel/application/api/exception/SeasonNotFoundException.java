package at.fhv.se.hotel.application.api.exception;

public class SeasonNotFoundException extends Exception {
    public SeasonNotFoundException(String message) {
        super(message);
    }
}
