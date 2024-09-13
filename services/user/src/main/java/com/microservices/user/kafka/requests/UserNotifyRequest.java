package com.microservices.user.kafka.requests;

import com.microservices.user.users.dto.UserDTO;
import lombok.Builder;

import java.io.Serializable;

/**
 * Record representing a notification request payload for user-related events.
 * It includes user information, a message, and the type of notification.
 *
 * <p>This record implements {@link Serializable} to allow instances to be serialized, which is useful when
 * transferring objects over the network or persisting them.
 *
 * @see com.microservices.user.users.dto.UserDTO
 * @see UserNotifyType
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record UserNotifyRequest(

        /**
         * The {@link UserDTO} object containing detailed user information.
         */
        UserDTO userInfo,

        /**
         * The message to be sent in the notification.
         */
        String message,

        /**
         * The type of notification, represented by {@link UserNotifyType}.
         */
        UserNotifyType type

) implements Serializable {
}
