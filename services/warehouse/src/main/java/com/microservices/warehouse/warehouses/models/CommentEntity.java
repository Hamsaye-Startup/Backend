package com.microservices.warehouse.warehouses.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_storage_comment")
@SequenceGenerator(name = "tb_storage_comment_seq", sequenceName = "tb_storage_comment_seq", initialValue = 1001, allocationSize = 1)
public class CommentEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tb_storage_comment_seq"
    )
    @Column(name = "comment_id", columnDefinition = "bigint", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(name = "comment_by", columnDefinition = "uuid", nullable = false, updatable = false)
    private UUID commentBy;

    @Column(name = "score", columnDefinition = "double precision")
    private Float score;

    @Column(name = "content", columnDefinition = "character varying", nullable = false, updatable = false)
    private String content;

    @Column(name = "comment_at", columnDefinition = "timestamp without time zone", nullable = false, updatable = false)
    private LocalDateTime commentAt;
}
