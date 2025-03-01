package org.rossijr.authentication.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when an object is not found.
 */
public class ObjectNotFoundException extends ApplicationWebGeneralException {
    public ObjectNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
