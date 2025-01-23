package org.rossijr.projecttemplate.exception;

import org.springframework.http.HttpStatus;

public class ObjectNotFoundException extends ApplicationWebGeneralException {
    public ObjectNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
