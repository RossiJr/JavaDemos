package org.rossijr.projecttemplate.exception;

public class ApplicationGeneralException extends RuntimeException {
    private final Integer httpCode;

    public ApplicationGeneralException(String message, Integer httpCode) {
        super(message);
        this.httpCode = httpCode;
    }
}
