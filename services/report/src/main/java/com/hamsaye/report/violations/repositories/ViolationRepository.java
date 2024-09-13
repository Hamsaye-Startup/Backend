package com.hamsaye.report.violations.repositories;

import com.hamsaye.report.violations.models.ViolationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ViolationRepository extends JpaRepository<ViolationEntity, Long> {
    Optional<ViolationEntity> findByTrackingNum(UUID trackingNum);
}
