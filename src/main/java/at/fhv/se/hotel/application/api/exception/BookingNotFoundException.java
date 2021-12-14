package at.fhv.se.hotel.application.api.exception;

public class BookingNotFoundException extends Exception {
    public BookingNotFoundException() {
        super();
    }

    public BookingNotFoundException(String message) {
        super(message);
    }
}
