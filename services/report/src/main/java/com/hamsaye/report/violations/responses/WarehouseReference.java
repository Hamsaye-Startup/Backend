package com.hamsaye.report.violations.responses;

import lombok.Builder;

@Builder
public record WarehouseReference(
        Long id
) implements ViolationReference {
}
