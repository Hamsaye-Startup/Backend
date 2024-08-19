package com.hamsaye.chat.kafka.models;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserDTO(
        UUID uid,
        String firstname,
        String lastname,
        String phone,
        UUID profilePictureId
) {
}
