package com.hamsaye.chat.kafka.models;

import lombok.Builder;

import java.util.UUID;

/**
 * Data Transfer Object (DTO) for user information.
 * <p>
 * This record represents a user with essential details such as their unique identifier,
 * name, phone number, and profile picture ID. It is used for transferring user data
 * between different layers of the application or between different services.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record UserDTO(
        UUID uid,
        String firstname,
        String lastname,
        String phone,

        /**
         * Unique identifier of the user's profile picture.
         */
        UUID profilePictureId
) {
}
