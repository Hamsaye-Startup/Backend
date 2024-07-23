package com.hamsaye.customer.customers.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record NewCustomerRequest(
        @JsonProperty("national_code")
        @NotNull(message = "national_code is required")
        String nid,
        String bio,
        @JsonProperty("user_id")
        @NotNull(message = "user_id is required")
        UUID userId
) {
}
