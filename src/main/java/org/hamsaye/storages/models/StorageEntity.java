package org.hamsaye.storages.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hamsaye.utils.functional.Functionality;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "tb_storages",
        indexes = {
                @Index(name = "storage_name_index", columnList = "storage_name")
        }
)
public class StorageEntity implements Serializable, Functionality {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "storage_uid", columnDefinition = "uuid", unique = true, nullable = false)
    private UUID uid;

    @Column(name = "storage_name", columnDefinition = "character varying", length = 64, nullable = false)
    private String name;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "timestamp", nullable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at", columnDefinition = "timestamp")
    private Timestamp modifiedAt;

    @Column(name = "width", columnDefinition = "integer", nullable = false)
    private Integer width;

    @Column(name = "height", columnDefinition = "integer", nullable = false)
    private Integer height;

    @Column(name = "max_weight", columnDefinition = "integer")
    private Integer maxWeight;

    @Column(name = "amount", columnDefinition = "numeric", nullable = false)
    private Double amount;

    @Column(name = "discount_amt", columnDefinition = "double precision", nullable = false)
    private Double discountAmount;

    @Column(name = "description", columnDefinition = "character varying", length = 1024)
    private String description;

    @Column(name = "status", columnDefinition = "character varying", length = 32, nullable = false)
    private String status;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_category_uid", columnDefinition = "uuid", nullable = false)
    private StorageCategoryEntity category;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "tb_features_storage",
            joinColumns = @JoinColumn(name = "fk_storage_uid", columnDefinition = "uuid"),
            inverseJoinColumns = @JoinColumn(name = "fk_feature_uid", columnDefinition = "uuid")
    )
    private List<StorageFeatureEntity> features;

    // TODO: generate a relationship with user
}
