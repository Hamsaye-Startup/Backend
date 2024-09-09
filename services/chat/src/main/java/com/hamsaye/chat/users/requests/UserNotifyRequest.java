package com.hamsaye.chat.users.requests;

import com.hamsaye.chat.users.models.UserDTO;
import lombok.Builder;

import java.io.Serializable;

/**
 * A request object used for notifying users within the chat application.
 * <p>
 * This record encapsulates the information required to send a notification to a user. It includes
 * the details of the user being notified, the message content, and the type of notification.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record UserNotifyRequest(

        /**
         * Details of the user to be notified.
         */
        UserDTO userInfo,

        /**
         * The message content to be sent as part of the notification.
         */
        String message,

        /**
         * The type of notification (e.g., alert, update).
         */
        UserNotifyType type

) implements Serializable {
}
