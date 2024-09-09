package com.microservices.user.customers.responses;

import com.microservices.user.users.responses.UserResponse;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a response containing details about a customer.
 * This record includes essential customer information such as their unique identifier, user details,
 * creation timestamp, national code, and biography.
 *
 * @param uid        the unique identifier of the customer.
 * @param user       the associated user details.
 * @param createdAt  the timestamp when the customer was created.
 * @param nid        the national identification code of the customer.
 * @param bio        the biography of the customer.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record CustomerResponse(
        UUID uid,
        UserResponse user,
        LocalDateTime createdAt,
        String nid,
        String bio

        // TODO: profile image (consider adding a field for the image URL or path)
) {
}
