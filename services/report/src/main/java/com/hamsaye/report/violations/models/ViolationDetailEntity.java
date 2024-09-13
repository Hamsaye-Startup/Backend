package com.hamsaye.report.violations.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "tb_violation_detail")
@SequenceGenerator(name = "tb_violation_detail_seq", sequenceName = "tb_violation_detail_seq", allocationSize = 1)
public class ViolationDetailEntity implements Serializable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tb_violation_detail_seq"
    )
    @Column(name = "detail_id", unique = true, nullable = false, columnDefinition = "bigint")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "track_number", referencedColumnName = "track_number", foreignKey = @ForeignKey(name = "fk_violation_track_detail"))
    private ViolationEntity violationEntity;

    @Column(name = "document_id", nullable = false)
    private String documentId; // handle by cloud storage
}
