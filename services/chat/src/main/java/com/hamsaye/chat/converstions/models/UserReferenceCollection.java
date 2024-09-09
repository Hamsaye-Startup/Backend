package com.hamsaye.chat.converstions.models;

import com.hamsaye.chat.users.models.UserRef;
import lombok.Builder;

/**
 * Represents a collection of user references associated with a conversation.
 * This record includes references to the user who started the conversation and the user who continued it.
 *
 * <p>It uses Lombok's {@code @Builder} annotation to facilitate the creation of immutable instances.
 *
 * @param starter The reference to the user who started the conversation.
 * @param continuator The reference to the user who continued the conversation.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record UserReferenceCollection(
        UserRef starter,
        UserRef continuator
) {
}
