package com.microservices.user.users.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

/**
 * Record representing a user request payload for creating or updating a user.
 * It includes phone number, first name, last name, and role identifier (UUID).
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record UserRequest(
        @NotBlank(message = "phone is required")
        String phone,

        @NotBlank(message = "firstname is required")
        String firstname,

        @NotBlank(message = "lastname is required")
        String lastname,

        /**
         * The role identifier for the user, represented as a UUID.
         */
        @JsonProperty("role_id")
        @NotNull(message = "role_id is required")
        UUID rid
) {
}
