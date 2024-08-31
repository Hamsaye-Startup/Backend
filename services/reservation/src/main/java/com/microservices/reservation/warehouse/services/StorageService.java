package com.microservices.reservation.warehouse.services;

import com.microservices.reservation.applications.messages.ResponseMessage;
import com.microservices.reservation.warehouse.client.StorageClient;
import com.microservices.reservation.warehouse.mappers.StorageMapper;
import com.microservices.reservation.warehouse.responses.StorageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StorageService {

    private final StorageClient storageClient;
    private final StorageMapper storageMapper;

    public StorageResponse findWarehouseById(Long id) {

        // map the message to response
        ResponseMessage message = storageClient.findWarehouseById(id, "TOKEN");
        return storageMapper.toResponse(message);
    }
}
