package com.microservices.gateway.role;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.util.Set;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;

public class RoleClient {

    @Value("${application.config.user-url}:http://localhost:8056")
    private static String url;
    private final RestClient client;
    public RoleClient() {
        client = RestClient.builder()
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .baseUrl(url)
                .build();
    }

    public Set<String> findAuthoritiesById(UUID uid) {
        return client.get()
                .uri("http://localhost:8056/api/v1/role/authorities/id/{id}", uid.toString())
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(ParameterizedTypeReference.forType(Set.class));
    }
}
