package com.microservices.reservation.warehouse.models;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_reservation_stats")
@SequenceGenerator(name = "tb_reservation_stats_seq", sequenceName = "tb_reservation_stats_seq", allocationSize = 4, initialValue = 1001)
public class ReservationStats {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tb_reservation_stats_seq"
    )
    @Column(name = "reservation_stats_id", columnDefinition = "bigint", unique = true, nullable = false, updatable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ConfirmReserveEnum confirmed;
}
