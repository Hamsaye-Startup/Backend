package com.hamsaye.chat.users.models;

import lombok.Builder;

import java.util.UUID;

/**
 * Data Transfer Object (DTO) representing a user in the chat system.
 * <p>
 * This record holds information about the user's identity, contact details,
 * attributes, and connection state in the system.
 * </p>
 *
 * @param uid              The unique identifier of the user.
 * @param firstname        The first name of the user.
 * @param lastname         The last name of the user.
 * @param profilePictureId  The UUID representing the user's profile picture.
 * @param contact          The contact information of the user, encapsulated in {@link UserContactInfo}.
 * @param userAttribute    The attributes of the user, encapsulated in {@link UserAttribute}.
 * @param connectionState  The current connection state of the user, represented by {@link UserConnectionState}.
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record UserDTO(
        UUID uid,
        String firstname,
        String lastname,
        UUID profilePictureId,
        UserContactInfo contact,
        UserAttribute userAttribute,
        UserConnectionState connectionState
) {
}
