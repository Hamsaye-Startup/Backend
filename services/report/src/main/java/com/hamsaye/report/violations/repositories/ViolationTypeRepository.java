package com.hamsaye.report.violations.repositories;

import com.hamsaye.report.violations.models.ViolationTypeCategory;
import com.hamsaye.report.violations.models.ViolationTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ViolationTypeRepository extends JpaRepository<ViolationTypeEntity, Long> {

    Optional<ViolationTypeEntity> findByCode(String code);

    List<ViolationTypeEntity> findAllByGroup(ViolationTypeCategory group);
}
