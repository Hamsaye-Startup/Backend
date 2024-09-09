package com.microservices.user.roles.responses;

import com.microservices.user.roles.models.UserAuthorityEnum;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record RoleResponse(
        UUID id,
        String name,
        Set<UserAuthorityEnum> authorities,
        LocalDateTime createAt
) {
}
