package at.fhv.se.hotel.application.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class represents an exception for the case that a room category can't be found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RoomCategoryNotFoundException extends Exception {
    public RoomCategoryNotFoundException(String message) {
        super(message);
    }
}
