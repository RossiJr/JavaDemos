package org.rossijr.authentication.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when the email is already in use.
 */
public class EmailAlreadyInUseExceptionWeb extends ApplicationWebGeneralException {
    public EmailAlreadyInUseExceptionWeb(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}