package com.hamsaye.report.users.mappers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hamsaye.report.applications.messages.ResponseMessage;
import com.hamsaye.report.users.responses.UserDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailMapper {

    private final ObjectMapper objectMapper;

    public Page<UserDetailResponse> toResponse(ResponseMessage message) {
        return objectMapper.convertValue(message.result(), new TypeReference<Page<UserDetailResponse>>() {});
    }
}
