package com.hamsaye.report.violations.client;

import com.hamsaye.report.applications.messages.ResponseMessage;
import com.hamsaye.report.users.mappers.UserDetailMapper;
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
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@RequiredArgsConstructor
public class UserClient {

    // TODO: implement secret

    @Value("${application.config.user-url:http://localhost:8222}")
    private static String url;

    private final UserDetailMapper userDetailMapper;

    private final RestClient client;

    @Autowired
    public UserClient(UserDetailMapper userDetailMapper) {
        this.userDetailMapper = userDetailMapper;
        client = RestClient.builder()
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .baseUrl(url)
                .build();
    }

    public List<UserDetailResponse> findOnlineUsersByRoleId(UUID roleId) {

        List<UserDetailResponse> onlineEmployees = new ArrayList<>();
        int pageNumber = 0;
        int pageSize = 100;
        boolean hasMorePages = true;

        String url = String.format(
                "%s/api/v1/user/online/role/id/%s/detail",
                UserClient.url,
                roleId.toString()
        );

        while (hasMorePages) {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);

            ResponseMessage response = findAllByUrl(url, pageable);
            if (response.result() == null) {
                hasMorePages = false;
            }
            else {
                List<UserDetailResponse> users = userDetailMapper.toResponse(response).getContent();
                if (users.isEmpty()) {
                    hasMorePages = false;
                } else {
                    onlineEmployees.addAll(users);
                }
            }
        }

        return onlineEmployees;
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
