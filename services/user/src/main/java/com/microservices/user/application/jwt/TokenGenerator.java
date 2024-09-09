package com.microservices.user.application.jwt;

import com.microservices.user.users.models.UserEntity;
import com.microservices.user.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Service class for generating JWT tokens including access and refresh tokens.
 *
 * <p>This service provides methods to generate access and refresh tokens with additional claims
 * based on the user details. It uses {@link JwtService} to handle the token generation and
 * {@link UserService} to fetch user details from the database.</p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class TokenGenerator {

    public static final int ACCESS_DURATION = 15 * 60 * 1000; // 15 minutes
    public static final int REFRESH_DURATION = 24 * 60 * 60 * 1000; // 24 hours

    private final JwtService service;
    private final UserService userService;

    /**
     * Generates additional claims for the token based on the user details.
     *
     * @param user the user details for which the claims are to be generated.
     * @return a map of claims including the user's role ID.
     */
    private Map<String, Object> generateExtraClaims(UserDetails user) {
        Map<String, Object> claims = new HashMap<>();
        UserEntity userEntity = userService.findByUid(UUID.fromString(user.getUsername()));

        claims.put("role", userEntity.getRole().getId());
        return claims;
    }

    /**
     * Generates additional claims for the token including extra claims.
     *
     * @param user the user details for which the claims are to be generated.
     * @param extraClaims additional claims to be included in the token.
     * @return a map of claims including the user's role ID and additional claims.
     */
    private Map<String, Object> generateExtraClaims(UserDetails user, Map<String, Object> extraClaims) {
        Map<String, Object> claims = generateExtraClaims(user);
        claims.putAll(extraClaims);
        return claims;
    }

    /**
     * Generates an access token for the user with the default access duration.
     *
     * @param user the user details for which the access token is to be generated.
     * @return the generated access token.
     */
    public String generateAccessToken(UserDetails user) {
        return service.generateToken(
                generateExtraClaims(user),
                user,
                ACCESS_DURATION
        );
    }

    /**
     * Generates an access token for the user with additional claims and the default access duration.
     *
     * @param user the user details for which the access token is to be generated.
     * @param extraClaims additional claims to be included in the token.
     * @return the generated access token.
     */
    public String generateAccessToken(UserDetails user, Map<String, Object> extraClaims) {
        return service.generateToken(
                generateExtraClaims(user, extraClaims),
                user,
                ACCESS_DURATION
        );
    }

    /**
     * Generates a refresh token for the user with the default refresh duration.
     *
     * @param user the user details for which the refresh token is to be generated.
     * @return the generated refresh token.
     */
    public String generateRefreshToken(UserDetails user) {
        return service.generateToken(
                generateExtraClaims(user),
                user,
                REFRESH_DURATION
        );
    }

    /**
     * Generates a refresh token for the user with additional claims and the default refresh duration.
     *
     * @param user the user details for which the refresh token is to be generated.
     * @param extraClaims additional claims to be included in the token.
     * @return the generated refresh token.
     */
    public String generateRefreshToken(UserDetails user, Map<String, Object> extraClaims) {
        return service.generateToken(
                generateExtraClaims(user, extraClaims),
                user,
                REFRESH_DURATION
        );
    }
}
