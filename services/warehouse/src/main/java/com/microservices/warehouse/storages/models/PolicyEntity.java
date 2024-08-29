package com.microservices.warehouse.storages.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

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

    @ManyToMany
    @JoinTable(
            name = "in_storage_policy",
            inverseJoinColumns = @JoinColumn(name = "fk_storage_id", referencedColumnName = "storage_id"),
            joinColumns = @JoinColumn(name = "fk_policy_id", referencedColumnName = "policy_id")
    )
    private Set<StorageEntity> storages;
}
