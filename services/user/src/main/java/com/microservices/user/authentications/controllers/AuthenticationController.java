package com.microservices.user.authentications.controllers;

import com.microservices.user.authentications.requests.AuthenticationRequest;
import com.microservices.user.authentications.responses.AuthenticationResponse;
import com.microservices.user.authentications.services.AuthenticationServiceManagement;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling authentication requests including user login and token refresh.
 *
 * Provides endpoints for authenticating a user and refreshing access tokens.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceManagement management;

    /**
     * Authenticates a user with the provided credentials and returns an access token along with a refresh token.
     *
     * @param authenticationRequest the {@link AuthenticationRequest} containing the username and password.
     * @param response the {@link HttpServletResponse} used to set the refresh token cookie.
     * @return a {@link ResponseEntity} containing the {@link AuthenticationResponse} with the access token and refresh token.
     * @since 1.0
     */
    @PostMapping
    public ResponseEntity<?> authenticate(
            @Valid @RequestBody AuthenticationRequest authenticationRequest,
            HttpServletResponse response
    ) {
        AuthenticationResponse authenticate = management.authenticate(
                authenticationRequest,
                response
        );
        return ResponseEntity.ok(authenticate);
    }

    /**
     * Refreshes the access token using the provided refresh token.
     *
     * @param token the refresh token used to generate a new access token.
     * @return a {@link ResponseEntity} containing the new {@link AuthenticationResponse} with the refreshed access token.
     * @since 1.0
     */
    @PostMapping("/token")
    public ResponseEntity<?> refreshToken(
            @RequestBody String token
    ) {
        AuthenticationResponse authenticate = management.refreshToken(token);
        return ResponseEntity.ok(authenticate);
    }
}
