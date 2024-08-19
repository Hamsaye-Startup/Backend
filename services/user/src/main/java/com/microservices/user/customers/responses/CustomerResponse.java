package com.microservices.user.customers.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microservices.user.users.responses.UserResponse;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record CustomerResponse(
        @JsonProperty("id")
        UUID uid,
        @JsonProperty("user")
        UserResponse user,
        @JsonProperty("created_at")
        LocalDateTime createdAt,
        @JsonProperty("national_code")
        String nid,
        String bio

        // todo image
) {
}
