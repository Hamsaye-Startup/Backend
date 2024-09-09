package com.hamsaye.chat.users.models;

import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

/**
 * A reference object representing a user in the chat system.
 * <p>
 * This record is used to reference a user with minimal information,
 * typically including the unique identifier and name of the user.
 * </p>
 *
 * @param uid  the unique identifier of the user
 * @param name the name of the user
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record UserRef(
        UUID uid,
        String name
) {
}
