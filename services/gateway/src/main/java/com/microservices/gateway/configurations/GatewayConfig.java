package com.microservices.gateway.configurations;

import com.microservices.gateway.filters.AuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up routes and filters in the Spring Cloud Gateway.
 *
 * This class defines the routing rules for different services and applies the {@link AuthorizationFilter}
 * to secure the routes based on the request paths.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final AuthorizationFilter filter;

    /**
     * Defines the routing configuration for the gateway.
     *
     * @param builder the {@link RouteLocatorBuilder} used to build the route configurations.
     * @return a {@link RouteLocator} containing the route definitions and their associated filters.
     * @since 1.0
     */
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/api/v1/user/**", "/api/v1/auth/**", "/api/v1/customer/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8056"))
                .route("warehouse-service", r -> r.path("/api/v1/warehouse/**", "/api/v1/feature/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8020"))
                .build();
    }
}
