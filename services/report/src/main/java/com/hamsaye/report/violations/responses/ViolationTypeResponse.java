package com.hamsaye.report.violations.responses;

import com.hamsaye.report.violations.models.ViolationPriority;
import com.hamsaye.report.violations.models.ViolationTypeCategory;
import lombok.Builder;

@Builder
public record ViolationTypeResponse(
        String code,
        String title,
        String desc,
        ViolationTypeCategory category,
        ViolationPriority priority
) {
}
