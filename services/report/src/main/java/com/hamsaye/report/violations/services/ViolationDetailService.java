package com.hamsaye.report.violations.services;

import com.hamsaye.report.violations.models.ViolationDetailEntity;
import com.hamsaye.report.violations.models.ViolationEntity;
import com.hamsaye.report.violations.repositories.ViolationDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ViolationDetailService {

    private final ViolationDetailRepository violationDetailRepository;

    public void insertViolationDetail(ViolationDetailEntity violationDetail) {
        violationDetailRepository.save(violationDetail);
    }

    public List<ViolationDetailEntity> findViolationDetailsByTrack(ViolationEntity violation) {
        return violationDetailRepository.findByViolationEntity(violation);
    }
}
