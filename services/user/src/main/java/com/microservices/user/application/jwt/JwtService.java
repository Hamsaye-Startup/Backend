package com.microservices.user.application.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * Service class for handling JWT (JSON Web Token) operations such as generation, validation,
 * and extraction of claims.
 *
 * <p>This service provides methods to generate tokens, extract claims, and validate token
 * authenticity. The token generation includes claims, subject, issuer, issued date, and
 * expiration date.</p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String SECRET_KEY = "150059a9896a3bf884277437c3b6b1d39b9283e8dc2f9ca668d61939ae019ae2";

    /**
     * Extracts the subject (username) from the JWT token.
     *
     * @param token the JWT token from which the subject is to be extracted.
     * @return the subject (username) contained in the token.
     */
    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Generates a JWT token with the specified claims, user details, and duration.
     *
     * @param claims a map of claims to be included in the token.
     * @param user the user details for whom the token is generated.
     * @param duration the duration in milliseconds for which the token is valid.
     * @return the generated JWT token.
     * @todo Encrypt the payload to enhance security.
     */
    public String generateToken(
            Map<String, Object> claims,
            UserDetails user,
            int duration
    ) {
        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
                .issuer("hamsaye-user-service")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + duration))
                .signWith(getSignInKey())
                .compact();
    }

    /**
     * Validates the JWT token based on the provided username.
     *
     * @param token the JWT token to be validated.
     * @param username the username to be checked against the token's subject.
     * @return true if the token is valid, false otherwise.
     */
    public boolean isTokenValid(String token, String username) {
        return ((username.equals(extractSubject(token)))
                && (!isTokenExpired(token))
                && (!isSessionExpired(token))
        );
    }

    /**
     * Checks whether the JWT token is expired.
     *
     * @param token the JWT token to be checked.
     * @return true if the token is expired, false otherwise.
     */
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date(System.currentTimeMillis()));
    }

    /**
     * Checks whether the JWT session is expired.
     *
     * @param token the JWT token to be checked.
     * @return false as session expiration is not yet implemented.
     */
    private boolean isSessionExpired(String token) {
        return false;
    }

    /**
     * Extracts a specific claim from the JWT token.
     *
     * @param token the JWT token from which the claim is to be extracted.
     * @param claimsResolver a function to resolve the claim from the token's claims.
     * @param <T> the type of the claim to be extracted.
     * @return the extracted claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts a specific claim from the JWT token based on the key.
     *
     * @param token the JWT token from which the claim is to be extracted.
     * @param key the key of the claim to be extracted.
     * @return the extracted claim.
     */
    public Object extractClaim(String token, String key) {
        Claims claims = extractAllClaims(token);
        return claims.get(key);
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
     * Generates the signing key used for validating the JWT token.
     *
     * @return the secret key used for signing the JWT token.
     */
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
