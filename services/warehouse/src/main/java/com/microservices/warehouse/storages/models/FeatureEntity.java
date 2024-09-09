package com.microservices.warehouse.storages.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * This class represents an entity for a storage feature. It includes details such as the feature's code, title, description,
 * and timestamps for creation and modification. The feature can be associated with multiple storage entities.
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
@Table(name = "tb_storage_feature")
@SequenceGenerator(
        name = "tb_storage_feature_seq",
        sequenceName = "tb_storage_feature_seq",
        initialValue = 1001,
        allocationSize = 3
)
public class FeatureEntity implements Serializable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tb_storage_feature_seq"
    )
    @Column(name = "feature_id", columnDefinition = "bigint", unique = true, nullable = false, updatable = false)
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

    @ManyToMany
    @JoinTable(
            name = "in_storage_feature",
            inverseJoinColumns = @JoinColumn(name = "fk_storage_id", referencedColumnName = "storage_id"),
            joinColumns = @JoinColumn(name = "fk_feature_id", referencedColumnName = "feature_id")
    )
    private Set<StorageEntity> storages;
}
