package com.hamsaye.report.violations.responses;

import com.hamsaye.report.violations.models.ViolationStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Builder
public record ViolationResponse(
        UUID track,
        UUID reporterId,
        Date incidentDate,
        String incidentDesc,
        ViolationTypeResponse type,
        ViolationStatus status,
        LocalDateTime createdAt,
        ViolationReference ref
) {
}
