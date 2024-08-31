package com.microservices.reservation.warehouse.client;

import com.microservices.reservation.applications.messages.ResponseMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class StorageClient {

    // TODO: implement secret

    @Value("${application.config.user-url:http://localhost:8222}")
    private static String url;
    private final RestClient client;

    public StorageClient() {
        client = RestClient.builder()
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .baseUrl(url)
                .build();
    }

    public ResponseMessage findWarehouseById(Long id, String token) {
        return client.get()
                .uri("/api/v1/storage/id/", id.toString())
                .accept(APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .body(ParameterizedTypeReference.forType(ResponseMessage.class));
    }
}
