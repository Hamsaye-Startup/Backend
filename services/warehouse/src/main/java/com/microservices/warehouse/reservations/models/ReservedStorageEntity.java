package com.microservices.warehouse.reservations.models;

import com.microservices.warehouse.storages.models.StorageEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_reserved_storage")
@SequenceGenerator(name = "tb_reserved_storage_seq", sequenceName = "tb_reserved_storage_seq", initialValue = 1001, allocationSize = 8)
public class ReservedStorageEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tb_reserved_storages_seq"
    )
    @Column(name = "reserved_storage_id", columnDefinition = "bigint", unique = true, nullable = false, updatable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "reserved_storage", columnDefinition = "bigint", unique = true, updatable = false)
    private StorageEntity storage;

    @OneToMany
    @JoinTable(
            name = "in_reserved_storage",
            joinColumns = @JoinColumn(name = "fk_reservation_id", referencedColumnName = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_reserved_storage_id", referencedColumnName = "reserved_storage_id")
    )
    private List<ReservationEntity> reservations;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "timestamp without time zone", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
