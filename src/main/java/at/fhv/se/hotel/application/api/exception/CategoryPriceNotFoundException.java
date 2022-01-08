package at.fhv.se.hotel.application.api.exception;

public class CategoryPriceNotFoundException extends RuntimeException {
    public CategoryPriceNotFoundException(String message) {
        super(message);
    }
}
