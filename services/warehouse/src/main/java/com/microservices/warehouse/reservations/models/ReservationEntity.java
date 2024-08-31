package com.microservices.warehouse.reservations.models;

import com.microservices.warehouse.storages.models.StorageCategoryEnum;
import com.microservices.warehouse.storages.models.StorageEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_reservations")
public class ReservationEntity implements Serializable {

    @Id
    @Column(name = "reservation_id", columnDefinition = "uuid", unique = true, nullable = false, updatable = false)
    private UUID reservationId;

    @Column(name = "reserved_by", columnDefinition = "uuid", nullable = false, updatable = false)
    private UUID reservedBy;

    @Column(name = "from_date", columnDefinition = "date", nullable = false)
    private LocalDate fromDate;

    @Column(name = "to_date", columnDefinition = "date", nullable = false)
    private LocalDate toDate;

    @ManyToOne
    @JoinColumn(name = "reserved_storage", columnDefinition = "bigint", unique = true, updatable = false)
    private StorageEntity storage;
}
