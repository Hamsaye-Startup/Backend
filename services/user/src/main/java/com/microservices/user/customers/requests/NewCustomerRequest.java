package com.microservices.user.customers.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

/**
 * Represents a request for creating a new customer.
 * Contains the necessary details required for creating a new customer record,
 * including the customer's national code, biography, gender, and associated user ID.
 *
 * @param nid      the national identification code of the customer (required).
 * @param bio      the biography of the customer (optional).
 * @param gender   the gender of the customer (optional).
 * @param userId   the unique identifier of the associated user (required).
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record NewCustomerRequest(
        @NotNull(message = "national_code is required")
        String nid,
        String bio,
        String gender,
        @NotNull(message = "user_id is required")
        UUID userId
) {
}
