package org.rossijr.authentication.auth;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Autowired
    public JwtRequestFilter(UserDetailsService userDetailsService,
                            JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Filters incoming HTTP requests to handle JWT-based authentication.
     *
     * <p>This method extracts the JWT token from the "Authorization" header of the request,
     * validates it, and sets the authenticated user in the {@link SecurityContextHolder}.
     * If the token is invalid or missing, the filter simply allows the request to proceed without authentication (this is important when having public endpoints).
     * </p>
     *
     * @param request  the incoming HTTP request
     * @param response the outgoing HTTP response
     * @param chain    the filter chain to pass the request and response to the next filter
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs during filtering
     * @see JwtUtil#verifyToken(String)
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt;

        try {
            // Check if Authorization header exists and starts with "Bearer "
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                // Skips the "Bearer " part of the header to extract the token
                jwt = authorizationHeader.substring(7);

                // Verify and decode the JWT
                DecodedJWT decodedJWT = jwtUtil.verifyToken(jwt);
                username = decodedJWT.getSubject();
            }

            // Checks if the username exists and ensures the user is not authenticated more than one time (other parts of the chain may rely on this object, so it's important to keep it consistent)
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // If the user exists, creates an authentication token and sets it in the Security Context
                if (userDetails != null) {
                    // "credentials" must be null, as there's no need (and it is not safe) to store the password in the token
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            // Continues the filter chain
            chain.doFilter(request, response);
        } catch (JWTVerificationException e) {
            // Handle invalid JWT
            logger.error("Invalid JWT: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
        } catch (Exception e) {
            // Handle general errors
            logger.error("Unexpected error during authentication", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Authentication processing error, please contact the administrator");
        }
    }

}
