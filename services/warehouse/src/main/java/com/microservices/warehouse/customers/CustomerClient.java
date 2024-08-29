package com.microservices.warehouse.customers;

import com.microservices.warehouse.applications.messages.ResponseMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;

public class CustomerClient {

    @Value("${application.config.user-url}:http://localhost:8222")
    private static String url;
    private final RestClient client;

    public CustomerClient() {
        client = RestClient.builder()
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .baseUrl(url)
                .build();
    }

    public ResponseMessage findCustomerById(UUID uid, String token) {
        return client.get()
                .uri("http://localhost:8222/api/v1/customer/id/{id}", uid.toString())
                .accept(APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .body(ParameterizedTypeReference.forType(ResponseMessage.class));
    }
}
