package com.hamsaye.chat.users.responses;

import com.hamsaye.chat.users.models.UserConnectionState;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@Builder
public record UserResponse(
        UUID uid,
        String firstname,
        String lastname,
        UUID profilePictureId,
        UserConnectionState connectionState
) {
}
