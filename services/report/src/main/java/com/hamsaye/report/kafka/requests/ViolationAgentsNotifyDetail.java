package com.hamsaye.report.kafka.requests;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ViolationAgentsNotifyDetail(
        UUID previousAgent,
        UUID currentAgent

) implements DistributedViolationNotifyDetail {
}
