package com.hamsaye.chat.messages.models;

import lombok.Builder;

/**
 * Data Transfer Object (DTO) for representing a message.
 * <p>
 * This record encapsulates the details of a message, including its unique identifier and content.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record MessageDTO(

        /**
         * Unique identifier of the message.
         */
        String id,
        String content
) {
}
