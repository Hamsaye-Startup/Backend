package com.microservices.reservation.warehouse.client;

import com.microservices.reservation.applications.messages.ResponseMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * This class is a client for interacting with the storage service.
 * It provides methods to perform HTTP requests to the storage service's API endpoints.
 *
 * <p>The client uses {@link RestClient} for making HTTP requests and is configured with a base URL
 * which can be overridden by the `application.config.user-url` property. The client is used to
 * retrieve storage information based on warehouse ID.</p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Component
public class StorageClient {

    // TODO: implement secret

    @Value("${application.config.user-url:http://localhost:8222}")
    private static String url;

    private final RestClient client;

    /**
     * Constructs a new {@code StorageClient} with the base URL and request factory configuration.
     * Initializes the {@link RestClient} used for making HTTP requests to the storage service.
     */
    public StorageClient() {
        client = RestClient.builder()
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .baseUrl(url)
                .build();
    }

    /**
     * Finds a warehouse by its ID.
     *
     * @param id the warehouse ID
     * @param token the authorization token for accessing the storage service
     * @return a {@link ResponseMessage} containing the response from the storage service
     */
    public ResponseMessage findWarehouseById(Long id, String token) {
        return client.get()
                .uri("/api/v1/storage/id/", id.toString())
                .accept(APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .body(ParameterizedTypeReference.forType(ResponseMessage.class));
    }
}
