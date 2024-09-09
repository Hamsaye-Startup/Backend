package com.microservices.reservation.warehouse.services;

import com.microservices.reservation.applications.messages.ResponseMessage;
import com.microservices.reservation.warehouse.client.StorageClient;
import com.microservices.reservation.warehouse.mappers.StorageMapper;
import com.microservices.reservation.warehouse.responses.StorageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class for interacting with storage-related operations in the reservation context.
 * <p>
 * This service provides methods to retrieve storage information by interacting with the storage client
 * and mapping the results to the appropriate response format.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class StorageService {

    /**
     * @see com.microservices.reservation.warehouse.client.StorageClient
     */
    private final StorageClient storageClient;

    /**
     * @see com.microservices.reservation.warehouse.mappers.StorageMapper
     */
    private final StorageMapper storageMapper;

    /**
     * Finds a warehouse by its unique identifier.
     * <p>
     * This method uses the {@link StorageClient} to retrieve storage information from an external service
     * and then maps the response to a {@link StorageResponse} using the {@link StorageMapper}.
     * </p>
     *
     * @param id the unique identifier of the warehouse
     * @return a {@link StorageResponse} containing the details of the warehouse
     * @since 1.0
     */
    public StorageResponse findWarehouseById(Long id) {

        // map the message to response
        ResponseMessage message = storageClient.findWarehouseById(id, "TOKEN");
        return storageMapper.toResponse(message);
    }
}
