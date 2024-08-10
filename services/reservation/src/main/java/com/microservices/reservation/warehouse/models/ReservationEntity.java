package com.microservices.reservation.warehouse.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_reservation_warehouse")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "reservation_id", columnDefinition = "uuid", unique = true, nullable = false, updatable = false)
    private UUID uid;

    @Column(name = "reserved_by", columnDefinition = "uuid", nullable = false, updatable = false)
    private UUID reservedBy;

    @Column(name = "warehouse_id", columnDefinition = "bigint", nullable = false, updatable = false)
    private Long warehouse;

    @Column(name = "owner", columnDefinition = "uuid", nullable = false, updatable = false)
    private UUID owner;

    @Column(name = "from_date", columnDefinition = "date", nullable = false)
    private LocalDate fromDate;

    @Column(name = "to_date", columnDefinition = "date", nullable = false)
    private LocalDate toDate;
/*
    @Column(name = "total_fees", columnDefinition = "numeric", nullable = false)
    private Double totalFees;*/

/*    @Column(name = "paid_amount", columnDefinition = "numeric", nullable = false)
    private Double AmountPaid;

    @Enumerated(EnumType.STRING)
    @Column(name = "for_each_date", columnDefinition = "character varying", length = 63, nullable = false)
    private ForEachDateEnum perDate;*/

/*    @Column(name = "total_installments_num", columnDefinition = "integer", nullable = false)
    private Integer totalInstallmentsNum;

    @Column(name = "paid_installments_num", columnDefinition = "integer", nullable = false)
    private Integer InstallmentsPaidNum;*/

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "timestamp without time zone", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at", columnDefinition = "timestamp without time zone", nullable = false, insertable = false)
    private LocalDateTime modifiedAt;
}
