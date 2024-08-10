package com.microservices.reservation.warehouse.services;

import com.microservices.reservation.applications.messages.ResponseMessage;
import com.microservices.reservation.warehouse.client.WarehouseClient;
import com.microservices.reservation.warehouse.mappers.WarehouseMapper;
import com.microservices.reservation.warehouse.responses.WarehouseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseClient warehouseClient;
    private final WarehouseMapper warehouseMapper;

    public WarehouseResponse findWarehouseById(Long id) {

        // map the message to response
        ResponseMessage message = warehouseClient.findWarehouseById(id, "TOKEN");
        return warehouseMapper.toResponse(message);
    }
}
