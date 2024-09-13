package com.microservices.user.users.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

/**
 * Represents a request for creating or updating a user detail.
 * Contains the necessary details for user management, including
 * the user's ID, national code, biography, and associated user ID.
 *
 * @param nid      the national identification code of the user (required).
 * @param bio      the biography of the user (optional).
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record UserDetailRequest (
        @NotNull(message = "national_code is required")
        String nid,
        String bio,
        String gender
) {
}
