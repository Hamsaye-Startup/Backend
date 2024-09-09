package com.microservices.warehouse.storages.responses;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * This record represents a response object for a storage's comment.
 * It provides details about the comment such as the ID, content, author, score, and display status.
 *
 * <p>NOTE: Clients can view this comment information, but only those comments marked as displayable will be shown to customers.</p>
 *
 * @param id The unique identifier of the comment.
 * @param commentBy The UUID of the user who made the comment.
 * @param score The rating score given in the comment.
 * @param content The actual content of the comment.
 * @param commentAt The timestamp indicating when the comment was made.
 * @param displayable A flag indicating whether the comment is displayable to customers. Only comments with a true value are visible.
 *
 * @version 1.0
 */
@Builder
public record CommentResponse(
        Long id,
        UUID commentBy,
        Float score,
        String content,
        LocalDateTime commentAt,

        /**
         * This flag is used for determining whether the comment is visible to customers.
         * Only comments with a true value are shown.
         */
        boolean displayable
) {
}
