package com.hamsaye.chat.kafka.requests;

import com.hamsaye.chat.kafka.models.UserDTO;
import lombok.Builder;

import java.io.Serializable;

/**
 * Represents a notification request for user-related updates sent over Kafka.
 * <p>
 * This record encapsulates details about user notifications, including user information,
 * an optional message, and the type of notification.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record UserNotifyRequest(

        /**
         * Contains the details of the user being notified.
         * <p>
         * This field holds the user information including their ID, name, phone, and profile picture ID.
         * </p>
         */
        UserDTO userInfo,

        /**
         * An optional additional message associated with the notification.
         * <p>
         * This field can be used to provide extra context or information related to the user notification.
         * </p>
         */
        String message,

        /**
         * The type of user notification.
         * <p>
         * This field specifies the kind of notification being sent, such as new user, user update, or user deletion.
         * </p>
         */
        UserNotifyType type

) implements Serializable {
}
