package org.rossijr.authentication.exception;

/**
 * Exception thrown when the email is already in use.
 */
public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException(String message) {
        super(message);
    }
}