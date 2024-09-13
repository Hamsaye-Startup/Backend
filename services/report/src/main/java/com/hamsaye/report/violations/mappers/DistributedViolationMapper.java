package com.hamsaye.report.violations.mappers;

import com.hamsaye.report.violations.models.DistributedViolationEntity;
import com.hamsaye.report.violations.responses.DistributedViolationResponse;
import org.springframework.stereotype.Service;

@Service
public class DistributedViolationMapper {
    public DistributedViolationResponse toResponse(
            DistributedViolationEntity distributedViolationEntity
    ) {
        return DistributedViolationResponse.builder()
                .track(distributedViolationEntity.getViolation().getTrackingNum())
                .agentId(distributedViolationEntity.getAgentId())
                .createdAt(distributedViolationEntity.getCreatedAt())
                .startedAt(distributedViolationEntity.getStartedAt())
                .finishedAt(distributedViolationEntity.getFinishedAt())
                .status(distributedViolationEntity.getStatus())
                .priority(distributedViolationEntity.getPriority())
                .build();
    }
}
