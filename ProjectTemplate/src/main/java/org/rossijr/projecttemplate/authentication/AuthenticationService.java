package org.rossijr.projecttemplate.authentication;


import com.auth0.jwt.exceptions.JWTVerificationException;
import org.rossijr.projecttemplate.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);


    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService,
                                 JwtUtil jwtUtil, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    private void updateLastLogin(String email) {
        try {
            userRepository.updateLastLogin(email);
        } catch (Exception e) {
            logger.error("An unexpected error occurred while updating the last login for user: {}", email);
            throw e;
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
            );
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getEmail());
            String jwt = jwtUtil.createToken(userDetails.getUsername());
            updateLastLogin(userDetails.getUsername());
            return new AuthenticationResponse(jwt);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (BadCredentialsException | UsernameNotFoundException e) {
            logger.warn("Invalid credentials for user: {}", authenticationRequest.getEmail());
            throw new BadCredentialsException(e.getMessage());
        } catch (AuthenticationException e) {
            logger.error("Authentication failed for user: {}", authenticationRequest.getEmail());
            throw e;
        } catch (Exception e) {
            logger.error("An unexpected error occurred while authenticating user: {}", authenticationRequest.getEmail());
            throw e;
        }
    }

    public String validateToken(String jwtToken) {
        try {
            if (jwtToken == null || jwtToken.trim().isEmpty()) {
                throw new IllegalArgumentException("Token is required");
            }
            jwtUtil.verifyToken(jwtToken);
            return "Token is valid";
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (JWTVerificationException e) {
            logger.warn("Token validation failed: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("An unexpected error occurred while validating the token: {}", e.getMessage());
            throw e;
        }
    }
}
