package com.hamsaye.report.kafka.requests;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ViolationNotifyRequest(
        UUID track,
        String message,
        ViolationNotifyType type
) {
}
