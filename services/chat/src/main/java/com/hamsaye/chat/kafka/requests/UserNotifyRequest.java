package com.hamsaye.chat.kafka.requests;

import com.hamsaye.chat.kafka.models.UserDTO;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record UserNotifyRequest(

        UserDTO userInfo,
        String message,
        UserNotifyType type

) implements Serializable {
}
