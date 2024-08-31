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

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceManagement management;

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

    @PostMapping("/token")
    public ResponseEntity<?> refreshToken(
            @RequestBody String token
    ) {
        AuthenticationResponse authenticate = management.refreshToken(token);
        return ResponseEntity.ok(authenticate);
    }
}
