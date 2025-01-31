package org.rossijr.authentication.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a general exception occurs, and it is related to web layer (not necessarily thrown by the web layer,
 * but it is related to it) - This general exception is also useful when working with GlobalExceptionHandler.
 */
public class ApplicationWebGeneralException extends RuntimeException {
    private final HttpStatus status;

    public ApplicationWebGeneralException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
