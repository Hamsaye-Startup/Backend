package com.hamsaye.chat.kafka.requests;

import lombok.Builder;

import java.util.UUID;

/**
 * Represents a notification request for messages sent over Kafka.
 * <p>
 * This record encapsulates the details about message notifications, including the message ID,
 * sender ID, conversation ID, message content, an optional message, and the type of notification.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record MessageNotifyRequest(
        String id,
        UUID senderId,

        /**
         * The unique identifier of the conversation to which the message belongs.
         * <p>
         * This field represents the ID of the conversation in which the message was sent.
         * </p>
         */
        UUID conversationId,

        /**
         * The content of the message.
         * <p>
         * This field contains the text or data of the message being notified.
         * </p>
         */
        String content,

        /**
         * An optional additional message associated with the notification.
         * <p>
         * This field can be used to provide extra context or information related to the notification.
         * </p>
         */
        String message,

        /**
         * The type of message notification.
         * <p>
         * This field specifies the kind of notification being sent, such as a new message or an
         * update to an existing message.
         * </p>
         */
        MessageNotifyType type

) {
}
