package com.hamsaye.chat.users.requests;

import com.hamsaye.chat.users.models.UserDTO;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record UserNotifyRequest(

        UserDTO userInfo,
        String message,
        UserNotifyType type

) implements Serializable {
}
