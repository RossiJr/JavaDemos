package org.rossijr.projecttemplate.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApplicationWebGeneralException {
    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
