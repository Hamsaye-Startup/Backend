package com.microservices.user.roles.requests;

import com.microservices.user.roles.model.UserAuthorityEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Set;

@Builder
public record NewRollRequest(
        @NotBlank(message = "name is required")
        String name,
        @NotNull(message = "set of authorities are required")
        Set<UserAuthorityEnum> authorities
) {
}
