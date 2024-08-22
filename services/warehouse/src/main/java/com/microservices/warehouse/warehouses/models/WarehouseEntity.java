package com.microservices.warehouse.warehouses.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_storages")
@SequenceGenerator(name = "tb_storages_seq", sequenceName = "tb_storages_seq", initialValue = 1001, allocationSize = 8)
public class WarehouseEntity implements Serializable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tb_storages_seq"
    )
    @Column(name = "storage_id", columnDefinition = "bigint", unique = true, nullable = false, updatable = false)
    private Long id;

    // TODO: features, privacy, category, location, images, owner
    @Column(name = "owner", columnDefinition = "uuid", unique = true, nullable = false, updatable = false)
    private UUID owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", columnDefinition = "character varying", length = 127, nullable = false)
    private CategoryEnum category;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "in_feature_storage",
            joinColumns = @JoinColumn(name = "fk_storage_id", referencedColumnName = "storage_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_feature_id", referencedColumnName = "feature_id")
    )
    private Set<FeatureEntity> features;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "in_policy_storage",
            joinColumns = @JoinColumn(name = "fk_storage_id", referencedColumnName = "storage_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_policy_id", referencedColumnName = "policy_id")
    )
    private Set<PolicyEntity> policies;

    @OneToOne
    @JoinColumn(name = "reserved_storage_id", columnDefinition = "bigint", unique = true)
    private ReservedWarehouseEntity reserved;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "timestamp without time zone", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at", columnDefinition = "timestamp without time zone", insertable = false)
    private LocalDateTime modifiedAt;

    @Column(name = "width", columnDefinition = "integer", nullable = false)
    private Integer width;

    @Column(name = "height", columnDefinition = "integer", nullable = false)
    private Integer height;

    @Column(name = "amount", columnDefinition = "numeric", nullable = false)
    private Double amount;

    @Column(name = "discount_amt", columnDefinition = "double precision", nullable = false)
    private Double discountAmount;

    @Column(name = "description", columnDefinition = "character varying", length = 1023)
    private String desc;

    @Transient
    private boolean marked;

    @Transient
    private boolean liked;
}
