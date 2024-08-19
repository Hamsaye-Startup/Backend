package com.hamsaye.chat.users.models;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserDTO(
        UUID uid,
        String firstname,
        String lastname,
        UUID profilePictureId,
        UserContactInfo contact,
        UserAttribute userAttribute,
        UserConnectionState connectionState
) {
}
