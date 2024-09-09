package com.microservices.reservation.warehouse.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.reservation.applications.messages.ResponseMessage;
import com.microservices.reservation.warehouse.responses.StorageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * This service class is responsible for mapping {@link ResponseMessage} objects to
 * {@link StorageResponse} objects using JSON conversion.
 *
 * <p>It utilizes {@link ObjectMapper} to handle the conversion of message results to
 * {@link StorageResponse} instances.</p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class StorageMapper {

    private final ObjectMapper objectMapper;

    /**
     * Converts a {@link ResponseMessage} to a {@link StorageResponse}.
     *
     * <p>This method uses the {@link ObjectMapper} to convert the result of the response message
     * to a {@link StorageResponse} object.</p>
     *
     * @param message the response message to convert
     * @return the corresponding {@link StorageResponse}
     * @since 1.0
     */
    public StorageResponse toResponse(ResponseMessage message) {
        return objectMapper.convertValue(message.result(), StorageResponse.class);
    }
}
