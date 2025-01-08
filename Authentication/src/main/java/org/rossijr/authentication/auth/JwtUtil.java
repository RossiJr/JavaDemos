package org.rossijr.authentication.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    // Inject the secret and expiration time from the application.properties file
    @Value("${jwt.secret}")
    private String SECRET;
    @Value("${jwt.expire.time}")
    private long expireTime;

    private Algorithm algorithm;

    // Initialize the algorithm with the secret key, it is @PostConstruct to ensure that the SECRET is initialized after
    // the SECRET is injected
    @PostConstruct
    public void init() {
        this.algorithm = Algorithm.HMAC256(SECRET);
    }

    /**
     * Verifies a given JWT token and returns the decoded token if valid.
     *
     * <p>This method uses the configured {@link JWTVerifier} to validate the token's signature,
     * structure, and claims. If the token is invalid, expired, or tampered with, the method
     * will throw a {@link JWTVerificationException}.</p>
     *
     * @param token the JWT token to be verified
     * @return a {@link DecodedJWT} object representing the verified token and its claims
     * @throws JWTVerificationException if the token verification fails
     */

    public DecodedJWT verifyToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    /**
     * Creates a JWT token with the specified subject.
     *
     * @param subject the subject for the token (typically a unique identifier for the user)
     * @return the generated JWT token as a String
     * @throws IllegalArgumentException if the subject is null or blank
     */
    public String createToken(String subject) {
        if (subject == null || subject.trim().isEmpty()) {
            throw new IllegalArgumentException("Subject cannot be null or blank");
        }
        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + expireTime);

        return JWT.create()
                .withSubject(subject)
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .sign(algorithm);
    }
}
