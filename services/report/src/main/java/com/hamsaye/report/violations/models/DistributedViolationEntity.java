package com.hamsaye.report.violations.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "tb_dist_violations")
@SequenceGenerator(name = "tb_dist_violations_seq", sequenceName = "tb_dist_violations_seq", allocationSize = 1)
public class DistributedViolationEntity implements Serializable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tb_dist_violations_seq"
    )
    @Column(name = "dist_violation_id", unique = true, nullable = false, columnDefinition = "bigint")
    private Long id;

    @OneToOne
    @JoinColumn(name = "track_number", referencedColumnName = "track_number", foreignKey = @ForeignKey(name = "fk_violation_track_dist"))
    private ViolationEntity violation;

    @Column(name = "agent_id", nullable = false, columnDefinition = "bigint")
    private UUID agentId;

    @Enumerated(EnumType.STRING)
    private ViolationResultStatus status;

    @Enumerated(EnumType.ORDINAL)
    private ViolationPriority priority;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "timestamp without time zone", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "started_at", columnDefinition = "timestamp without time zone")
    private LocalDateTime startedAt;

    @Column(name = "finished_at", columnDefinition = "timestamp without time zone")
    private LocalDateTime finishedAt;
}
