package com.hamsaye.report.violations.client;

import com.hamsaye.report.applications.messages.ResponseMessage;
import com.hamsaye.report.roles.mappers.RoleMapper;
import com.hamsaye.report.roles.responses.RoleResponse;
import com.hamsaye.report.users.responses.UserDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@RequiredArgsConstructor
public class RoleClient {

    // TODO: implement secret

    @Value("${application.config.user-url:http://localhost:8222}")
    private static String url;

    private final RoleMapper roleMapper;

    private final RestClient client;

    @Autowired
    public RoleClient(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
        client = RestClient.builder()
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .baseUrl(url)
                .build();
    }

    public List<RoleResponse> findAllRoles() {

        List<RoleResponse> allRoles = new ArrayList<>();
        int pageNumber = 0;
        int pageSize = 100;
        boolean hasMorePages = true;

        String url = String.format(
                "%s/api/v1/role",
                RoleClient.url
        );

        while (hasMorePages) {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);

            ResponseMessage response = findAllByUrl(url, pageable);
            if (response.result() == null) {
                hasMorePages = false;
            }
            else {
                List<RoleResponse> roles = roleMapper.toResponse(response).getContent();
                if (roles.isEmpty()) {
                    hasMorePages = false;
                } else {
                    allRoles.addAll(roles);
                }
            }
        }

        return allRoles;
    }

    private ResponseMessage findAllByUrl(String url, Pageable pageable) {
        return client.get()
                .uri(uriBuilder -> uriBuilder.path(url)
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize())
                        .build())
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(ParameterizedTypeReference.forType(ResponseMessage.class));
    }
}
