package com.microservices.warehouse.warehouses.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_storage_policy")
public class PolicyEntity implements Serializable {

    @Id
    @Column(name = "policy_id", columnDefinition = "bigint", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(name = "code", columnDefinition = "character varying", length = 7, unique = true, nullable = false, updatable = false)
    private String code;

    @Column(name = "title", columnDefinition = "character varying", length = 127, nullable = false)
    private String title;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "timestamp without time zone", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at", columnDefinition = "timestamp without time zone", insertable = false)
    private LocalDateTime modifiedAt;

    @Column(name = "description", columnDefinition = "character varying", length = 1023)
    private String desc;

    @Column(name = "documentation", columnDefinition = "character varying")
    private String doc;
}
