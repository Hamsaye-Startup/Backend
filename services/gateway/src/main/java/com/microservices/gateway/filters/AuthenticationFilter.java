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

@RefreshScope
@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements GatewayFilter {

    private final RouterValidator routerValidator;
    private final JwtService service;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (routerValidator.isSecured.test(request)) {
            // get the authorization header
            String authorization = request.getHeaders()
                    .getFirst(HttpHeaders.AUTHORIZATION);

            // check the bearer format
            if (this.checkMissingHeader(authorization)) {
                return this.onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            String token = authorization.substring(7);
            String username = service.extractSubject(token);
            if (this.checkValueExist(username)) {

                if (service.isTokenValid(token, username)) {

                    // check the role
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

    // check the missing authorization header
    private boolean checkMissingHeader(String header) {
        return header == null || !header.startsWith("Bearer ");
    }

    // check username
    private boolean checkValueExist(String value) {
        return value != null;
    }

    // generate the X_USER_N X_ROLE_A header
    private void updateRequest(ServerWebExchange exchange, String username, Collection<String> authorities) {
        exchange.getRequest().mutate()
                .header("X_USER_N", username)
                .header("X_ROLE_A", Arrays.toString(authorities.toArray()))
                .build();
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        return response.setComplete();
    }
}
