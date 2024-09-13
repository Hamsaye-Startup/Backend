package com.hamsaye.report.violations.services;

import com.hamsaye.report.users.responses.UserDetailResponse;
import com.hamsaye.report.violations.models.DistributedViolationEntity;
import com.hamsaye.report.violations.models.ViolationEntity;
import com.hamsaye.report.violations.models.ViolationResultStatus;
import com.hamsaye.report.violations.repositories.DistributedViolationRepository;
import com.hamsaye.report.violations.responses.DistributedViolationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DistributedViolationService {

    private final DistributedViolationRepository distributedRepository;

    public DistributedViolationEntity insertViolation(DistributedViolationEntity distributed) {
        return distributedRepository.save(distributed);
    }

    public void updateViolationDateTime(
            DistributedViolationEntity distributed,
            LocalDateTime dateTime,
            boolean changeStartedDateTime
    ) {
        if (changeStartedDateTime) {
            distributed.setStartedAt(dateTime);
        }
        else {
            distributed.setFinishedAt(dateTime);
        }
        distributedRepository.save(distributed);
    }

    public void updateViolationResult(
            DistributedViolationEntity distributed,
            ViolationResultStatus status
    ) {
        distributed.setStatus(status);
        distributedRepository.save(distributed);
    }

    public void updateViolationAgent(
            DistributedViolationEntity distributed,
            UUID agentId
    ) {
        distributed.setAgentId(agentId);
        distributedRepository.save(distributed);
    }

    public Page<DistributedViolationEntity> findViolationByAgentId(UUID agentId, Pageable pageable) {
        return distributedRepository.findAllByAgentIdOrderByPriorityAndDateAndStatus(
                agentId,
                pageable
        );
    }

    public Integer findOnProcessViolationByAgentId(UUID agentId) {
        return distributedRepository.findAllOnProcessViolationByAgentId(agentId);
    }

    public DistributedViolationEntity findViolationByTrack(ViolationEntity violation) {
        return distributedRepository.findByViolation(violation)
                .orElseThrow(() -> new RuntimeException()); // TODO: generate specific exception
    }

    public DistributedViolationEntity findLastViolation() {
        return distributedRepository.findTopByOrderByCreatedAtDesc()
                .orElseThrow(() -> new RuntimeException()); // TODO: generate specific exception
    }
}
