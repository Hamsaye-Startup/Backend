package com.hamsaye.report.violations.services;

import com.hamsaye.report.violations.models.ViolationTypeCategory;
import com.hamsaye.report.violations.models.ViolationTypeEntity;
import com.hamsaye.report.violations.repositories.ViolationTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ViolationTypeService {

    private final ViolationTypeRepository violationTypeRepository;

    public ViolationTypeEntity findViolationTypeByCode(String code) {
        return violationTypeRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException()); // TODO: generate specific exception
    }

    public List<ViolationTypeEntity> findViolationTypesByCategory(ViolationTypeCategory category) {
        return violationTypeRepository.findAllByGroup(category);
    }
}
