package com.hamsaye.report.roles.responses;

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
        Set<String> authorities,
        LocalDateTime createAt
) {
}
