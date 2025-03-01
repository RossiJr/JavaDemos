package org.rossijr.authentication.auth.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * This class is used to handle the data from the validate request.
 */
public class ValidateRequestDTO {
    @NotBlank(message = "Token is required")
    private String token;

    public ValidateRequestDTO() {
    }

    public String getToken() {
        return token;
    }
}
