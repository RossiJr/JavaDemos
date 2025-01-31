package org.rossijr.authentication.auth;

import jakarta.validation.Valid;
import org.rossijr.authentication.auth.dto.AuthenticationRequestDTO;
import org.rossijr.authentication.auth.dto.AuthenticationResponseDTO;
import org.rossijr.authentication.auth.dto.ValidateRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Authenticates a user based on the provided credentials.
     *
     * <p>This method authenticates a user based on the provided username and password. For this, the method invokes a
     * method from the {@link AuthenticationService} class (one layer down) to authenticate the user. If the user is
     * successfully authenticated, the method returns a JWT token, wrapped inside the {@link AuthenticationResponseDTO}
     * class, alongside with the user ID. If the authentication fails, the method returns an error response.</p>
     *
     * @param authenticationRequestDTO the authentication request containing username and password
     * @return a {@link AuthenticationResponseDTO} containing the JWT token and user ID
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@Valid @RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequestDTO));
    }

    /**
     * Validates a given JWT token.
     *
     * <p>This method validates a given JWT token. For this, the method invokes a method from the {@link AuthenticationService}
     * class (one layer down) to validate the token. If the token is valid, the method returns a success response. If the
     * token is invalid, the method returns an error response.</p>
     *
     * @param token the token to be validated
     * @return a success response if the token is valid, an error response otherwise
     */
    @PostMapping("/validate")
    public ResponseEntity<Object> validateToken(@RequestBody ValidateRequestDTO token) {
        return ResponseEntity.ok(authenticationService.validateToken(token.getToken()));

    }
}
