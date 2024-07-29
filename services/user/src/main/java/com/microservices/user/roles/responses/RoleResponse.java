package com.microservices.user.roles.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microservices.user.roles.models.UserAuthorityEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
public record RoleResponse(
        UUID id,
        String name,
        Set<UserAuthorityEnum> authorities,
        @JsonProperty("create_at")
        LocalDateTime createAt
) {
}
