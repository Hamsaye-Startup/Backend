package com.microservices.user.authentications.services;

import com.microservices.user.application.exceptions.ExpiredTokenException;
import com.microservices.user.authentications.requests.AuthenticationRequest;
import com.microservices.user.authentications.responses.AuthenticationResponse;
import com.microservices.user.application.jwt.JwtService;
import com.microservices.user.application.jwt.TokenGenerator;
import com.microservices.user.users.exceptions.IllegalRequestException;
import com.microservices.user.users.models.UserEntity;
import com.microservices.user.users.services.UserDetailsServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceManagement {

    private final AuthenticationManager authenticationManager;
    private final JwtService service;
    private final TokenGenerator generator;
    private final UserDetailsServiceImpl userDetailsService;

    // authentication process:: username password authentication
    public AuthenticationResponse authenticate(
            AuthenticationRequest request,
            HttpServletResponse response
    ) {

        UserEntity user = userDetailsService.loadUserByPhone(request.username());

        // authenticate user
        authenticationManager.authenticate (
                new UsernamePasswordAuthenticationToken(
                        user.getUid(),
                        request.password()
                )
        );

        // generate access token and refresh token
        String access = generator.generateAccessToken(user);
        String refresh = generator.generateRefreshToken(user);

        // return refresh token as cookie
        Cookie cookie = generateRefreshCookie(refresh, response);

        return AuthenticationResponse.builder()
                .accessToken(access)
                .expiredIn(TokenGenerator.ACCESS_DURATION)
                .refresh(cookie.getName())
                .tokenType("Bearer")
                .build();
    }

    // refresh the access token with the refresh token
    public AuthenticationResponse refreshToken(String token) {
        String username = service.extractSubject(token);

        // check null pointer exception
        if (username == null) {
            throw new IllegalRequestException(token);
        }

        // check the token is valid
        if (service.isTokenValid(token, username)) {
            throw new ExpiredTokenException(username);
        }

        // check the authentication
        UserDetails user = userDetailsService.loadUserByUsername(username);
        if (SecurityContextHolder.getContext().getAuthentication() == null) {

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities()
                    );
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // generate new access token
        String access = generator.generateAccessToken(user);
        return AuthenticationResponse.builder()
                .accessToken(access)
                .expiredIn(TokenGenerator.ACCESS_DURATION)
                .refresh("HAMSAYE_TOKEN")
                .tokenType("Bearer")
                .build();
    }

    // add refresh toke as new cookie
    private Cookie generateRefreshCookie(String token, HttpServletResponse response) {
        Cookie cookie = new Cookie("HAMSAYE_TOKEN", token);

        cookie.setMaxAge(TokenGenerator.REFRESH_DURATION);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);
        return cookie;
    }
}
