package com.microservices.reservation.products.responses;

import com.microservices.reservation.products.models.ProductTypeEntity;
import com.microservices.reservation.warehouse.responses.ReservationResponse;
import lombok.Builder;

/**
 * Represents the response containing details of a product.
 * This record includes information about the product's type, price, description, and associated reservation.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record ProductResponse(
        ProductTypeEntity type,

        Double price,

        /**
         * A description of the product.
         */
        String desc,
        ReservationResponse reservation
) {
}
