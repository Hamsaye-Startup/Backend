package com.microservices.user.users.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * Record representing a registration request payload.
 * It includes fields for phone number, password, first name, and last name,
 * all of which are required for creating a new user account.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record RegistrationRequest(
        @NotBlank(message = "phone is required")
        String phone,

        @NotBlank(message = "password is required")
        String password,

        @NotBlank(message = "firstname is required")
        String firstname,

        @NotBlank(message = "lastname is required")
        String lastname
) {
}
