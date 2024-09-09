package com.microservices.gateway.configurations;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

/**
 * Component responsible for validating whether a request URI is secured or open.
 *
 * This component contains a list of open API endpoints that do not require authorization.
 * It provides a predicate to determine if a given request URI is secured based on whether
 * it matches any of the open endpoints.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 * @since 1.0
 */
@Component
public class RouterValidator {

    /**
     * A list of API endpoints that are considered open and do not require authorization.
     */
    public static final List<String> openApiEndpoints = List.of(
            "/api/v1/auth",
            "/api/v1/auth/**",
            "/api/v1/user/register"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
