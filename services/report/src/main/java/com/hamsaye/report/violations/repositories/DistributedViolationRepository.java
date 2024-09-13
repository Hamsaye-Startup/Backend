package com.hamsaye.report.violations.repositories;

import com.hamsaye.report.violations.models.DistributedViolationEntity;
import com.hamsaye.report.violations.models.ViolationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DistributedViolationRepository extends JpaRepository<DistributedViolationEntity, Long> {

    @Query("select v from DistributedViolationEntity v " +
            "where " +
            "v.agentId = :agentId " +
            "order by " +
            "v.priority asc, " +
            "v.createdAt desc, " +
            "case " +
            "when v.status is null then 0 " +
            "when v.status is not null then 1 " +
            "end asc")
    Page<DistributedViolationEntity> findAllByAgentIdOrderByPriorityAndDateAndStatus(
            @Param("agentId") UUID agentId,
            Pageable pageable
    );

    Optional<DistributedViolationEntity> findByViolation(ViolationEntity violation);

    @Query("select count(v) from DistributedViolationEntity v " +
            "where " +
            "v.agentId = :agentId and " +
            "v.status is null")
    Integer findAllOnProcessViolationByAgentId(UUID agentId);

    Optional<DistributedViolationEntity> findTopByOrderByCreatedAtDesc();
}
