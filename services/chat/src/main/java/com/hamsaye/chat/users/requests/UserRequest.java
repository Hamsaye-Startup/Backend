package com.hamsaye.chat.users.requests;

import com.hamsaye.chat.users.models.ConnectionStatus;
import lombok.Builder;

import java.io.Serializable;
import java.util.UUID;

@Builder
public record UserRequest(

        UUID uid,
        String firstname,
        String lastname,
        String phone,
        UUID profilePictureId

) implements Serializable {
}
