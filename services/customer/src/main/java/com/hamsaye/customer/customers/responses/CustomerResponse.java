package com.hamsaye.customer.customers.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hamsaye.customer.customers.models.CustomerStatus;
import lombok.Builder;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record CustomerResponse(
        @JsonProperty("id")
        UUID uid,
        @JsonProperty("user_id")
        UUID userId,
        @JsonProperty("created_at")
        LocalDateTime createdAt,
        @JsonProperty("national_code")
        String nid,
        String bio,
        List<CustomerStatus> status

        // todo image
) {
}
