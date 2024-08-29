package com.microservices.warehouse.storages.models;

import com.microservices.warehouse.geos.models.AddressEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_storages")
@SequenceGenerator(name = "tb_storages_seq", sequenceName = "tb_storages_seq", initialValue = 1001, allocationSize = 8)
public class StorageEntity implements Serializable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tb_storages_seq"
    )
    @Column(name = "storage_id", columnDefinition = "bigint", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(name = "owner", columnDefinition = "uuid", unique = true, nullable = false, updatable = false)
    private UUID owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", columnDefinition = "character varying", length = 127, nullable = false)
    private StorageCategoryEnum category;

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

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private AddressEntity address;

    @Column(name = "description", columnDefinition = "character varying", length = 1023)
    private String desc;

    @Column(name = "enabled", nullable = false)
    private boolean enabled; // This feature issues permission to display on the site

    @Enumerated(EnumType.STRING)
    private StorageStatusEnum status;

    @Enumerated(EnumType.STRING)
    private StorageVerifiedEnum verified;

    @Transient
    private boolean marked;

    @Transient
    private boolean liked;
}
