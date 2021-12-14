package at.fhv.se.hotel.application.api.exception;

public class ServiceNotFoundException extends Exception {
    public ServiceNotFoundException() {
    }

    public ServiceNotFoundException(String message) {
        super(message);
    }
}
