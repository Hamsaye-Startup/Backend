package com.microservices.warehouse.storages.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * This class represents an entity for a storage policy. It includes details such as the policy's code, title, description,
 * documentation link, and timestamps for creation and modification. The policy can be associated with multiple storage entities.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */

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

    /**
     * This is key used for searching. It is an abbreviation of title.
     */
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

    /**
     * The policy can have a document file stored in cloud services.
     */
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
