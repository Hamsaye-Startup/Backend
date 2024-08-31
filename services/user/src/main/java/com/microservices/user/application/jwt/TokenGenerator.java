package com.microservices.user.application.jwt;

import com.microservices.user.users.models.UserEntity;
import com.microservices.user.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenGenerator {

    public static final int ACCESS_DURATION = 15 * 60 * 1000;
    public static final int REFRESH_DURATION = 24 * 60 * 60 * 1000;

    private final JwtService service;
    private final UserService userService;

    private Map<String, Object> generateExtraClaims(UserDetails user) {
        Map<String, Object> claims = new HashMap<>();
        UserEntity userEntity = userService.findByUid(UUID.fromString(user.getUsername()));

        claims.put("role", userEntity.getRole().getId());
        return claims;
    }

    private Map<String, Object> generateExtraClaims(UserDetails user, Map<String, Object> extraClaims) {
        Map<String, Object> claims = generateExtraClaims(user);
        claims.putAll(extraClaims);
        return claims;
    }

    public String generateAccessToken(UserDetails user) {
        return service.generateToken(
                generateExtraClaims(user),
                user,
                ACCESS_DURATION
        );
    }

    public String generateAccessToken(UserDetails user, Map<String, Object> extraClaims) {
        return service.generateToken(
                generateExtraClaims(user, extraClaims),
                user,
                ACCESS_DURATION
        );
    }

    public String generateRefreshToken(UserDetails user) {
        return service.generateToken(
                generateExtraClaims(user),
                user,
                REFRESH_DURATION
        );
    }

    public String generateRefreshToken(UserDetails user, Map<String, Object> extraClaims) {
        return service.generateToken(
                generateExtraClaims(user, extraClaims),
                user,
                REFRESH_DURATION
        );
    }
}
