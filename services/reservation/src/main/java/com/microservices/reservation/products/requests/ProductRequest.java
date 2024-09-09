package com.microservices.reservation.products.requests;

import lombok.Builder;

/**
 * Represents a request to create or update a product.
 * This record contains information about the product's type, price, and description.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record ProductRequest(
        Long type,
        Double price,

        /**
         * A description of the product.
         */
        String desc
) {
}
