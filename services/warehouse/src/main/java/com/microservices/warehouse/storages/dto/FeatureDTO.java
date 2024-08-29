package com.microservices.warehouse.storages.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record FeatureDTO(

        @NotBlank(message = "code is required")
        String code,
        @NotBlank(message = "title is required")
        String title,
        String description
) {
}
