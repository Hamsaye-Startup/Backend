package com.microservices.user.users.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RegistrationRequest(
        @NotBlank(message = "phone is required")
        String phone,
        @NotBlank(message = "password is required")
        String password,
        @NotBlank(message = "firstname is required")
        String firstname,
        @NotBlank(message = "lastname is required")
        String lastname
) {
}
