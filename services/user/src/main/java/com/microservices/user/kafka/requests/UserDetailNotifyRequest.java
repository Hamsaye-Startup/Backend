package com.microservices.user.kafka.requests;

import com.microservices.user.users.dto.UserDetailDTO;
import lombok.Builder;

import java.io.Serializable;

/**
 * Represents a request for notifying about a user event.
 * Contains the details of the user, the message to be sent,
 * and the type of notification.
 *
 * @param message      the notification message.
 * @param type         the type of notification.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record UserDetailNotifyRequest(
        UserDetailDTO userInfo,
        String message,
        UserDetailNotifyType type

) implements Serializable {
}
