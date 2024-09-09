package com.hamsaye.chat.users.responses;

import com.hamsaye.chat.users.models.UserConnectionState;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

/**
 * A response object representing the user data to be sent in responses.
 * <p>
 * This record contains the essential details about a user, including their unique identifier,
 * name, profile picture ID, and current connection state. It is used for communication between
 * the chat application components and is typically returned in API responses or messages.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record UserResponse(
        UUID uid,
        String firstname,
        String lastname,

        /**
         * ID representing the user's profile picture.
         */
        UUID profilePictureId,

        /**
         * The current connection state of the user (e.g., connected or disconnected).
         */
        UserConnectionState connectionState
) {
}
