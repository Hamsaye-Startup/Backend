package com.hamsaye.report.kafka.requests;

import lombok.Builder;

import java.util.UUID;

@Builder
public record DistributedViolationNotifyRequest(
        UUID track,
        DistributedViolationNotifyDetail detail,
        String message,
        DistributedViolationNotifyType type
) {
}
