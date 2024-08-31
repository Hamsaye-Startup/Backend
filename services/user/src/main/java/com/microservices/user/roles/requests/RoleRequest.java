package com.microservices.user.roles.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microservices.user.roles.models.UserAuthorityEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record RoleRequest(
        @NotBlank(message = "name is required")
        String name,
        @NotNull(message = "set of authorities are required")
        Set<UserAuthorityEnum> authorities
) {
}
