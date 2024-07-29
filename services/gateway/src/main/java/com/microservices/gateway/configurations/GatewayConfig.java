package com.microservices.gateway.configurations;

import com.microservices.gateway.filters.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/api/v1/user/**", "/api/v1/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8056"))
                .route("warehouse-service", r -> r.path("/api/v1/warehouse/**", "/api/v1/feature/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8020"))

                .build();
    }
}
