package org.rossijr.projecttemplate.exception;

public class EmailAlreadyInUseException extends ApplicationGeneralException {
    public EmailAlreadyInUseException(String message) {
        super(message, 400);
    }

    public EmailAlreadyInUseException(String message, Integer httpCode) {
        super(message, httpCode);
    }
}
