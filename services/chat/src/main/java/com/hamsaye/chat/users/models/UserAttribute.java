package com.hamsaye.chat.users.models;

import lombok.Builder;

/**
 * Represents attributes associated with a user in the chat system.
 * <p>
 * This record is used to store additional information about the user, such as gender.
 * </p>
 *
 * @param gender The gender of the user, represented by {@link GenderEnum}.
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record UserAttribute(
        GenderEnum gender
) {
}
