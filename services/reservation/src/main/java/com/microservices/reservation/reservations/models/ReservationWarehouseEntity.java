package com.microservices.reservation.reservations.models;

import com.microservices.reservation.reservations.mappers.StringListConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_reservation_warehouse")
public class ReservationWarehouseEntity {

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

    @Convert(converter = StringListConverter.class)
    @Column(name = "addresses", nullable = false)
    private List<Date> InstallmentsDate;

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

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "timestamp without time zone", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at", columnDefinition = "timestamp without time zone", nullable = false, insertable = false)
    private LocalDateTime modifiedAt;
}
