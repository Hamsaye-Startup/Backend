package com.microservices.reservation.reservations.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_reservation_warehouse_history")
public class ReservationWarehouseHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "reservation_id", columnDefinition = "uuid", unique = true, nullable = false, updatable = false)
    private UUID uid;

    @Column(name = "reserved_by", columnDefinition = "uuid", nullable = false, updatable = false)
    private UUID reservedBy;

    @Column(name = "warehouse_id", columnDefinition = "bigint", nullable = false, updatable = false)
    private Long warehouse;

    @Column(name = "from_date", columnDefinition = "date", nullable = false)
    private Date fromDate;

    @Column(name = "to_date", columnDefinition = "date", nullable = false)
    private Date toDate;

    @Column(name = "total_fees", columnDefinition = "numeric", nullable = false)
    private Double totalFees;

    @Column(name = "rent_per", columnDefinition = "numeric", nullable = false)
    private Double rentPer;

    @Column(name = "paid_amount", columnDefinition = "numeric", nullable = false)
    private Double AmountPaid;

    @Enumerated(EnumType.STRING)
    @Column(name = "for_each_date", columnDefinition = "character varying", length = 63, nullable = false)
    private ForEachDateEnum perDate;

    @Column(name = "total_installments_num", columnDefinition = "integer", nullable = false)
    private Integer totalInstallmentsNum;

    @Column(name = "paid_installments_num", columnDefinition = "integer", nullable = false)
    private Integer InstallmentsPaidNum;

    @Column(name = "status", columnDefinition = "character varying", length = 31, nullable = false)
    private String status;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "timestamp without time zone", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
