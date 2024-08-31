package com.microservices.reservation.warehouse.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.reservation.applications.messages.ResponseMessage;
import com.microservices.reservation.warehouse.responses.StorageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StorageMapper {

    private final ObjectMapper objectMapper;

    public StorageResponse toResponse(ResponseMessage message) {
        return objectMapper.convertValue(message.result(), StorageResponse.class);
    }
}
