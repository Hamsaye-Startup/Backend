package com.microservices.user.roles.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record RoleResponse(
        UUID id,
        String name,
        @JsonProperty("create_at")
        LocalDateTime createAt
) {
}
