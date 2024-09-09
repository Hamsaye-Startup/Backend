package com.microservices.gateway.role;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.util.Set;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * Client class for interacting with the role management service.
 *
 * This client communicates with the role management service to retrieve authorities by user ID.
 * It uses a {@link RestClient} to send HTTP requests to the service.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
public class RoleClient {

    @Value("${application.config.user-url:http://localhost:8056}")
    private static String url;

    private final RestClient client;

    public RoleClient() {
        client = RestClient.builder()
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .baseUrl(url)
                .build();
    }

    /**
     * Finds authorities for a given user ID by sending a GET request to the role management service.
     *
     * @param uid the user ID for which authorities are to be retrieved.
     * @return a {@link Set} of authorities for the specified user ID.
     * @since 1.0
     */
    public Set<String> findAuthoritiesById(UUID uid) {
        return client.get()
                .uri("/api/v1/role/authorities/id/{id}", uid.toString())
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(ParameterizedTypeReference.forType(Set.class));
    }
}
