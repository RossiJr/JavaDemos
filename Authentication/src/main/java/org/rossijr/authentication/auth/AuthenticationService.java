package org.rossijr.authentication.auth;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.rossijr.authentication.auth.dto.AuthenticationRequestDTO;
import org.rossijr.authentication.auth.dto.AuthenticationResponseDTO;
import org.rossijr.authentication.repository.UserRepository;
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

    /**
     * Updates the last login date for the user with the provided email.
     *
     * @param email the email of the user
     */
    private void updateLastLogin(String email) {
        try {
            userRepository.updateLastLogin(email);
        } catch (Exception e) {
            logger.error("An unexpected error occurred while updating the last login for user: {}", email);
            throw e;
        }
    }

    /**
     * Authenticates the user with the provided email and password.
     * <p>The login logic is handled by the {@link AuthenticationManager} and the {@link CustomUserDetailsService} classes.</p>
     * <p>Here, the method first authenticates the user by invoking the {@link AuthenticationManager#authenticate} method, then
     * retrieves the user details by invoking the {@link CustomUserDetailsService#loadUserByUsername} method. If the user is
     * successfully authenticated, the method creates a JWT token by invoking the {@link JwtUtil#createToken} method, updates
     * the last login date for the user, and returns the token and user ID wrapped inside the {@link AuthenticationResponseDTO}
     * class. If the authentication fails, the method throws an exception.</p>
     *
     * @param authenticationRequestDTO the authentication request containing the user email and password
     * @return an {@link AuthenticationResponseDTO} containing the JWT token and user ID
     */
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO authenticationRequestDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequestDTO.getEmail(), authenticationRequestDTO.getPassword())
            );
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequestDTO.getEmail());
            String jwt = jwtUtil.createToken(userDetails.getUsername());
            updateLastLogin(userDetails.getUsername());
            return new AuthenticationResponseDTO(jwt, userRepository.findByEmail(userDetails.getUsername()).getId());
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (BadCredentialsException | UsernameNotFoundException e) {
            logger.warn("Invalid credentials for user: {}", authenticationRequestDTO.getEmail());
            throw new BadCredentialsException(e.getMessage());
        } catch (AuthenticationException e) {
            logger.error("Authentication failed for user: {}", authenticationRequestDTO.getEmail());
            throw e;
        } catch (Exception e) {
            logger.error("An unexpected error occurred while authenticating user: {}", authenticationRequestDTO.getEmail());
            throw e;
        }
    }

    /**
     * Validates the provided JWT token.
     *
     * <p>The method validates the provided JWT token by invoking the {@link JwtUtil#verifyToken} method. If the token is valid,
     * the method returns a success message. If the token is invalid, the method throws an exception.</p>
     *
     * @param jwtToken the JWT token to be validated
     * @return a success message if the token is valid
     */
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
