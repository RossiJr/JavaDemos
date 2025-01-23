package org.rossijr.projecttemplate.exception;

import org.springframework.http.HttpStatus;

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
