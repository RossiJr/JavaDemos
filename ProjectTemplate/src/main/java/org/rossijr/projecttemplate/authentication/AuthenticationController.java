package org.rossijr.projecttemplate.authentication;

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
@RequestMapping("/api/v1/authentication")
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

    @PostMapping("/login")
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
