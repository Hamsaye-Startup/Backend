package com.hamsaye.report.violations.repositories;

import com.hamsaye.report.violations.models.ViolationDetailEntity;
import com.hamsaye.report.violations.models.ViolationEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ViolationDetailRepository extends JpaRepository<ViolationDetailEntity, Long> {
    
    List<ViolationDetailEntity> findByViolationEntity(ViolationEntity violationEntity);
}
