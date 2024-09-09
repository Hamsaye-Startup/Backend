package com.microservices.gateway.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

/**
 * Service class for handling JWT (JSON Web Token) operations.
 *
 * This service provides methods to extract claims from tokens, validate tokens, and generate the signing key.
 * It uses the JWT library to parse and verify tokens and handle JWT-specific operations.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String SECRET_KEY = "150059a9896a3bf884277437c3b6b1d39b9283e8dc2f9ca668d61939ae019ae2";

    /**
     * Extracts the subject (e.g., phone number) from the JWT token.
     *
     * @param token the JWT token from which the subject is to be extracted.
     * @return the subject of the token.
     */
    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Validates the JWT token by checking the subject, expiration, and session status.
     *
     * @param token the JWT token to be validated.
     * @param username the username to be compared with the subject extracted from the token.
     * @return true if the token is valid, otherwise false.
     */
    public boolean isTokenValid(String token, String username) {
        return ((username.equals(extractSubject(token)))
                && (!isTokenExpired(token))
                && (!isSessionExpired(token))
        );
    }

    /**
     * Checks if the JWT token has expired.
     *
     * @param token the JWT token to be checked.
     * @return true if the token has expired, otherwise false.
     */
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date(System.currentTimeMillis()));
    }

    /**
     * Checks if the session associated with the JWT token has expired.
     *
     * @param token the JWT token to be checked.
     * @return false, as session expiration handling is not implemented.
     */
    private boolean isSessionExpired(String token) {
        return false;
    }

    /**
     * Extracts a specific claim from the JWT token based on the provided claims resolver function.
     *
     * @param <T> the type of the claim.
     * @param token the JWT token from which the claim is to be extracted.
     * @param claimsResolver the function to resolve the claim from the claims.
     * @return the extracted claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts a specific claim from the JWT token based on the provided key and class type.
     *
     * @param <T> the type of the claim.
     * @param token the JWT token from which the claim is to be extracted.
     * @param key the key associated with the claim.
     * @param aClass the class type of the claim.
     * @return the extracted claim.
     */
    public <T> T extractClaim(String token, String key, Class<T> aClass) {
        Claims claims = extractAllClaims(token);
        return claims.get(key, aClass);
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token the JWT token from which the claims are to be extracted.
     * @return the claims extracted from the token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Generates the signing key for JWT token validation.
     *
     * @return the SecretKey used for signing and validating tokens.
     */
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
