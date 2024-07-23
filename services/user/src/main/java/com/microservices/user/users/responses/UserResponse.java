package com.microservices.user.users.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record UserResponse(
        UUID uid,
        String phone,
        String firstname,
        String lastname,
        UUID rid,
        @JsonProperty("created_at")
        LocalDateTime createAt
) {
}
