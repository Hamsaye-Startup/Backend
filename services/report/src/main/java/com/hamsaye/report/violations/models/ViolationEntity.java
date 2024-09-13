package com.hamsaye.report.violations.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "tb_violations")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="violation_entity_type", discriminatorType = DiscriminatorType.STRING)
@SequenceGenerator(name = "tb_violations_seq", sequenceName = "tb_violations_seq", allocationSize = 1)
public class ViolationEntity implements Serializable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tb_violations_seq"
    )
    @Column(name = "violation_id", unique = true, nullable = false, columnDefinition = "bigint")
    private Long id;

    @Column(name = "track_number", unique = true, nullable = false, columnDefinition = "uuid")
    private UUID trackingNum;

    @Column(name = "report_by", nullable = false, columnDefinition = "uuid")
    private UUID reporterId;

    @Column(name = "incident_date", nullable = false, columnDefinition = "date")
    private Date incidentDate;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "timestamp without time zone", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at", columnDefinition = "timestamp without time zone", insertable = false)
    private LocalDateTime modifiedAt;

    @Column(name = "incident_description", columnDefinition = "character varying", length = 511)
    private String incidentDesc;

    @ManyToOne
    @JoinColumn(name = "violation_type", foreignKey = @ForeignKey(name = "fk_violation_type_id_violate"))
    private ViolationTypeEntity type;

    @Enumerated(EnumType.STRING)
    private ViolationStatus status;
}
