package com.microservices.user.authentications.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record AuthenticationRequest(
        @NotBlank(message = "username is required")
        String username,
        @NotBlank(message = "password is required")
        String password
) {
}
