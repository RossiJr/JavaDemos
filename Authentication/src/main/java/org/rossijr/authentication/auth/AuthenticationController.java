package org.rossijr.authentication.auth;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authenticate")
public class AuthenticationController {
    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    CustomUserDetailsService userDetailsService,
                                    JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Authenticates a user based on the provided credentials.
     *
     * <p>This method validates the {@link AuthenticationRequest} object, performs authentication
     * using the {@link AuthenticationManager}, and generates a JWT token if authentication is successful.
     * It handles authentication failures and unexpected errors gracefully.</p>
     *
     * @param authenticationRequest the authentication request containing username and password
     * @return a {@link ResponseEntity} containing the JWT token or an error response
     */
    @PostMapping
    public ResponseEntity<Object> authenticate(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
            UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            String jwt = jwtUtil.createToken(userDetails.getUsername());
            logger.info("User authenticated: {}", authenticationRequest.getUsername());
            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        } catch (BadCredentialsException e) {
            logger.info("Invalid username or password for user: {}", authenticationRequest.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (AuthenticationException e) {
            logger.error("Authentication failed for user: {}", authenticationRequest.getUsername());
            return ResponseEntity.internalServerError().body("Authentication failed");
        } catch (Exception e) {
            logger.error("An unexpected error occurred while authenticating user: {}", authenticationRequest.getUsername());
            return ResponseEntity.internalServerError().body("An unexpected error occurred while authenticating user, contact the administrator");
        }
    }

    /**
     * Validates a given JWT token.
     *
     * <p>This endpoint verifies the validity of a provided JSON Web Token (JWT). It returns
     * a success message if the token is valid, or an error message if the token is invalid or
     * an error occurs during validation.</p>
     *
     * @param jwtToken the JWT token to validate
     * @return a {@link ResponseEntity} containing a success or error message
     */
    @PostMapping("/validate")
    public ResponseEntity<Object> validateToken(@RequestParam String jwtToken) {
        try {
            if (jwtToken == null || jwtToken.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Token cannot be null or empty");
            }
            jwtUtil.verifyToken(jwtToken);
            return ResponseEntity.ok("Token is valid");
        } catch (JWTVerificationException e) {
            logger.warn("Token validation failed: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid token");
        } catch (Exception e) {
            logger.error("An unexpected error occurred while validating the token: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("An unexpected error occurred while validating the token, contact the administrator");
        }

    }
}
