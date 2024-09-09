package com.microservices.user.users.dto;

import lombok.Builder;

import java.util.UUID;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record UserDTO(
        UUID uid,
        String firstname,
        String lastname,
        String phone,
        UUID profilePictureId
) {
}
