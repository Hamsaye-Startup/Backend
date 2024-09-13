package com.hamsaye.report.roles.mappers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hamsaye.report.applications.messages.ResponseMessage;
import com.hamsaye.report.roles.responses.RoleResponse;
import com.hamsaye.report.users.responses.UserDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleMapper {

    private final ObjectMapper objectMapper;

    public Page<RoleResponse> toResponse(ResponseMessage message) {
        return objectMapper.convertValue(message.result(), new TypeReference<Page<RoleResponse>>() {});
    }
}
