package org.rossijr.authentication.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.rossijr.authentication.auth.JwtUtil;
import org.rossijr.authentication.model.User;
import org.rossijr.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class SecurityUtils {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Autowired
    public SecurityUtils(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    /**
     * @param token
     * @return
     */
    public UUID getAuthenticatedUserId(String token) {
        try {
            // Checks if the base of the token is valid (if it is not null and contains the "Bearer " prefix)
            if (token == null || !token.startsWith("Bearer ")) {
                throw new RuntimeException("Invalid token");
            }

            // Extracts the token from the "Bearer " prefix
            token = token.replace("Bearer ", "").trim();

            // Verifies if the rest of the token is valid and extracts the email from the token
            DecodedJWT decodedJWT = jwtUtil.verifyToken(token);
            String email = decodedJWT.getSubject();

            // Checks if the email is not null and retrieves the user id
            if (email != null) {
                User user = Optional.ofNullable(userService.getUserByEmail(email)).orElseThrow(() -> new UsernameNotFoundException("User not found"));
                return user.getId();
            } else {
                throw new RuntimeException("E-mail not found in JWT");
            }
        } catch (UsernameNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Invalid or expired JWT token", e);
        }
    }

}
