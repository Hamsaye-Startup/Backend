package com.hamsaye.report.violations.responses;

import com.hamsaye.report.violations.models.ViolationPriority;
import com.hamsaye.report.violations.models.ViolationResultStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record DistributedViolationResponse(
        UUID track,
        UUID agentId,
        LocalDateTime createdAt,
        LocalDateTime startedAt,
        LocalDateTime finishedAt,
        ViolationResultStatus status,
        ViolationPriority priority
) {
}
