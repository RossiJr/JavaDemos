package org.rossijr.authentication.auth.dto;


import java.util.UUID;

/**
 * This class is used to return the JWT token to the client, along with the user ID for future requests.
 */
public class AuthenticationResponseDTO {
    private final String token;
    private final UUID userId;

    public AuthenticationResponseDTO(String token, UUID userId) {
        this.token = token;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public UUID getUserId() {
        return userId;
    }
}