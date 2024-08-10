package com.microservices.reservation.warehouse.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.reservation.applications.messages.ResponseMessage;
import com.microservices.reservation.warehouse.responses.WarehouseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WarehouseMapper {

    private final ObjectMapper objectMapper;

    public WarehouseResponse toResponse(ResponseMessage message) {
        return objectMapper.convertValue(message.result(), WarehouseResponse.class);
    }
}
