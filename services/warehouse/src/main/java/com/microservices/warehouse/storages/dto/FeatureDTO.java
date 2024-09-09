package com.microservices.warehouse.storages.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * This record represents a Data Transfer Object (DTO) for the Feature entity.
 * The code and title fields are mandatory and must not be blank.
 *
 * @param code The unique code of the feature, which is required.
 * @param title The title of the feature, which is required.
 * @param description A description of the feature, which is optional.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record FeatureDTO(

        @NotBlank(message = "code is required")
        String code,
        @NotBlank(message = "title is required")
        String title,
        String description
) {
}
