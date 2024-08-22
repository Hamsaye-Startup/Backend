package com.microservices.warehouse.warehouses.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_reserved_storages")
@SequenceGenerator(name = "tb_reserved_storages_seq", sequenceName = "tb_reserved_storages_seq", initialValue = 1001, allocationSize = 8)
public class ReservedWarehouseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tb_reserved_storages_seq"
    )
    @Column(name = "reserved_storage_id", columnDefinition = "bigint", unique = true, nullable = false, updatable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "reserved_warehouse", columnDefinition = "bigint", unique = true, updatable = false)
    private WarehouseEntity warehouse;

    @OneToMany
    @JoinTable(
            name = "in_warehouse_reserved",
            joinColumns = @JoinColumn(name = "fk_reserved_warehouse_id", referencedColumnName = "reserved_storage_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_reservation_id", referencedColumnName = "reservation_id")
    )
    private List<ReservationEntity> reservations;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "timestamp without time zone", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
