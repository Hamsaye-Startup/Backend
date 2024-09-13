package com.hamsaye.report.violations.services;

import com.hamsaye.report.violations.models.ViolationEntity;
import com.hamsaye.report.violations.models.ViolationStatus;
import com.hamsaye.report.violations.repositories.ViolationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ViolationService {

    private final ViolationRepository violationRepository;

    public <T extends ViolationEntity> void insertViolation(T violation) {
        violationRepository.save(violation);
    }

    public void deleteViolation(ViolationEntity violation) {
        violationRepository.delete(violation);
    }

    public ViolationEntity findViolationByTrack(UUID track) {
        return violationRepository.findByTrackingNum(track)
                .orElseThrow(() -> new RuntimeException()); // TODO: generate specific exception
    }

    public void updateViolationStatus(
            ViolationEntity violation,
            ViolationStatus violationStatus
    ) {
        violation.setStatus(violationStatus);
        violationRepository.save(violation);
    }
}
