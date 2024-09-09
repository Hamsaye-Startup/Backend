package com.microservices.user.customers.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

/**
 * Represents a request for creating or updating a customer.
 * Contains the necessary details for customer management, including
 * the customer's ID, national code, biography, and associated user ID.
 *
 * @param uid      the unique identifier for the customer (used for updates).
 * @param nid      the national identification code of the customer (required).
 * @param bio      the biography of the customer (optional).
 * @param userId   the unique identifier of the associated user (required).
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record CustomerRequest(
        UUID uid,
        @NotNull(message = "national_code is required")
        String nid,
        String bio,
        @NotNull(message = "user_id is required")
        UUID userId
) {
}
