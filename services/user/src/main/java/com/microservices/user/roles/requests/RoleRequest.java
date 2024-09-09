package com.microservices.user.roles.requests;

import com.microservices.user.roles.models.UserAuthorityEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Set;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record RoleRequest(
        @NotBlank(message = "name is required")
        String name,
        @NotNull(message = "set of authorities are required")
        Set<UserAuthorityEnum> authorities
) {
}
