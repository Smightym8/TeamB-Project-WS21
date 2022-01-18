package at.fhv.se.hotel.application.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class represents an exception for the case that a guest can't be found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class GuestNotFoundException extends Exception {
    public GuestNotFoundException(String message) {
        super(message);
    }
}
