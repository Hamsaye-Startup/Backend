package com.hamsaye.report.violations.responses;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserReference(
        UUID id
) implements ViolationReference {
}
