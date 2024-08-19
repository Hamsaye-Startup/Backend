package com.microservices.user.users.requests;

import com.microservices.user.users.models.UserDTO;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record UserNotifyRequest(

        UserDTO userInfo,
        String message,
        UserNotifyType type

) implements Serializable {
}
