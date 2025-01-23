package org.rossijr.projecttemplate.exception;

import org.springframework.http.HttpStatus;

public class EmailAlreadyInUseExceptionWeb extends ApplicationWebGeneralException {
    public EmailAlreadyInUseExceptionWeb(String message) {
        super(message, HttpStatus.CONFLICT);
    }

    public EmailAlreadyInUseExceptionWeb(String message, HttpStatus status) {
        super(message, status);
    }
}
