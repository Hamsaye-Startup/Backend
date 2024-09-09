package com.microservices.warehouse.storages.requests;

import lombok.Builder;

import java.util.UUID;

/**
 * This class is a request class for storage's comment.
 * NOTE: The request data type object provided by client.
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record CommentRequest(

        /**
        * This feature is the reservation's id exists in reservation dataset.
        */
        UUID reservationId,
        Float score,
        String content
) {
}
