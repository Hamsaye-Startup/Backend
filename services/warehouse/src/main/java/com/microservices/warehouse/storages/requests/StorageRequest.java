package com.microservices.warehouse.storages.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record StorageRequest(
        @NotBlank(message = "category is required")
        String category,
        @NotNull(message = "width is required")
        Integer width,
        @NotNull(message = "height is required")
        Integer height,
        @NotNull(message = "amount is required")
        Double amount,
        @NotNull(message = "discountAmount is required")
        Double discountAmount,
        String desc
) {
}
