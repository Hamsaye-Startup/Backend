package com.microservices.user.passwords.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record PasswordRequest(
        @JsonProperty("id")
        @NotNull(message = "id is required")
        UUID uid,
        @NotBlank(message = "password is required")
        String password
) {
}
