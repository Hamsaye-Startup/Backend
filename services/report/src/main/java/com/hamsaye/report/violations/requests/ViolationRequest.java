package com.hamsaye.report.violations.requests;

import lombok.Builder;

import java.util.Date;

@Builder
public record ViolationRequest(
        String type,
        Date incidentDate,
        String incidentDesc
) {
}
