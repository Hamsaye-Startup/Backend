package com.microservices.reservation.warehouse.models;

import jakarta.persistence.*;
import lombok.*;

/**
 * This class represents the reservation statistics in the warehouse system.
 * It includes information about the confirmation status of the reservation.
 *
 * <p>This class is mapped to the "tb_reservation_stats" table and uses a sequence generator
 * for auto-incrementing the ID field. The confirmation status is represented by the
 * {@link ConfirmReserveEnum} enumeration.</p>
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
