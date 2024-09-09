package com.microservices.gateway.filters;

import com.microservices.gateway.configurations.RouterValidator;
import com.microservices.gateway.jwt.JwtService;
import com.microservices.gateway.role.RoleClient;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

/**
 * A Spring Cloud Gateway filter that performs authorization checks based on JWT tokens.
 *
 * This filter intercepts requests, validates JWT tokens, extracts user information, and updates
 * request headers with user ID and roles if the token is valid. It also handles errors by
 * responding with appropriate HTTP status codes.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@RefreshScope
@Component
@RequiredArgsConstructor
public class AuthorizationFilter implements GatewayFilter {

    private final RouterValidator routerValidator;
    private final JwtService service;

    /**
     * Filters requests to check for authorization using JWT tokens.
     *
     * @param exchange the current server web exchange.
     * @param chain the gateway filter chain.
     * @return a {@link Mono<Void>} indicating the completion of the filter processing.
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (routerValidator.isSecured.test(request)) {
            // Get the authorization header
            String authorization = request.getHeaders()
                    .getFirst(HttpHeaders.AUTHORIZATION);

            // Check the bearer format
            if (this.checkMissingHeader(authorization)) {
                return this.onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            String token = authorization.substring(7);
            String username = service.extractSubject(token);
            if (this.checkValueExist(username)) {

                if (service.isTokenValid(token, username)) {

                    // Check the role
                    String roleId = service.extractClaim(token, "role", String.class);
                    Collection<String> authorities;
                    if (checkValueExist(roleId)) {
                        RoleClient client = new RoleClient();
                        UUID rollUid = UUID.fromString(roleId);
                        authorities = client.findAuthoritiesById(rollUid);

                        updateRequest(exchange, username, authorities);
                    }
                } else {
                    return this.onError(exchange, HttpStatus.FORBIDDEN);
                }
            }
        }
        return chain.filter(exchange);
    }

    /**
     * Checks if the authorization header is missing or not in the proper format.
     *
     * @param header the authorization header.
     * @return true if the header is missing or not properly formatted, otherwise false.
     */
    private boolean checkMissingHeader(String header) {
        return header == null || !header.startsWith("Bearer ");
    }

    /**
     * Checks if a value is not null.
     *
     * @param value the value to check.
     * @return true if the value is not null, otherwise false.
     */
    private boolean checkValueExist(String value) {
        return value != null;
    }

    /**
     * Updates the request headers with the user ID and roles.
     *
     * @param exchange the current server web exchange.
     * @param username the username to be added to the header.
     * @param authorities the collection of roles to be added to the header.
     */
    private void updateRequest(ServerWebExchange exchange, String username, Collection<String> authorities) {
        exchange.getRequest().mutate()
                .header("X_USER_ID", username)
                .header("X_ROLE_A", Arrays.toString(authorities.toArray()))
                .build();
    }

    /**
     * Sends an error response with the specified HTTP status code.
     *
     * @param exchange the current server web exchange.
     * @param status the HTTP status code to set on the response.
     * @return a {@link Mono<Void>} indicating the completion of the error response processing.
     */
    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        return response.setComplete();
    }
}
