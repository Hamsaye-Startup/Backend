package com.microservices.warehouse.warehouses.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_reservations")
public class ReservationEntity implements Serializable {

    @Id
    @Column(name = "reservation_id", columnDefinition = "uuid", unique = true, nullable = false, updatable = false)
    private UUID reservationId;

    @Column(name = "reserved_by", columnDefinition = "uuid", nullable = false, updatable = false)
    private UUID reservedBy;

    @Column(name = "to_date", columnDefinition = "date", nullable = false)
    private LocalDate toDate;
}
