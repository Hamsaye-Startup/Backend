package com.microservices.user.users.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Record representing a response payload for user-related data.
 * It includes user ID, phone number, first name, last name, role ID, and creation timestamp.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record UserResponse(
        UUID uid,
        String phone,
        String firstname,
        String lastname,

        /**
         * The role identifier for the user, represented as a UUID.
         */
        UUID rid,
        LocalDateTime createAt
) {
}
