package com.microservices.warehouse.storages.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * This record represents a Data Transfer Object (DTO) for the Policy entity.
 * The code and title fields are mandatory and must not be blank.
 *
 * @param code The unique code of the policy, which is required.
 * @param title The title of the policy, which is required.
 * @param description A description of the policy, which is optional.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record PolicyDTO(
        @NotBlank(message = "code is required")
        String code,
        @NotBlank(message = "title is required")
        String title,
        String description
) {
}
