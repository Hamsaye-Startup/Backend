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
@Table(name = "tb_violation_type")
@SequenceGenerator(name = "tb_violation_type_seq", sequenceName = "tb_violation_type_seq", allocationSize = 1)
public class ViolationTypeEntity implements Serializable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tb_violation_type_seq"
    )
    @Column(name = "violation_type_id", unique = true, nullable = false, columnDefinition = "bigint")
    private Long id;

    @Column(name = "violation_id", unique = true, nullable = false, columnDefinition = "character varying", length = 15)
    private String code;

    @Column(name = "title", nullable = false, columnDefinition = "character varying", length = 127)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private ViolationTypeCategory group;

    @Enumerated(EnumType.STRING)
    private ViolationPriority priority;

    @Column(name = "description", columnDefinition = "character varying", length = 255)
    private String desc;
}
