package org.rossijr.authentication.auth;


/**
 * This class is used to return the JWT token to the client.
 */
public class AuthenticationResponse {

    private final String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}