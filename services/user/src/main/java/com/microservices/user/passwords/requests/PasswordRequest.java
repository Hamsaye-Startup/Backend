package com.microservices.user.passwords.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record PasswordRequest(
        @JsonProperty("id")
        @NotNull(message = "id is required")
        UUID uid,
        @NotBlank(message = "password is required")
        String password
) {
}
