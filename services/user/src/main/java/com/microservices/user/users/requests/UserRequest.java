package com.microservices.user.users.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record UserRequest(
        @NotBlank(message = "phone is required")
        String phone,
        @NotBlank(message = "firstname is required")
        String firstname,
        @NotBlank(message = "lastname is required")
        String lastname,
        @JsonProperty("role_id")
        @NotNull(message = "role_id is required")
        UUID rid
) {
}
