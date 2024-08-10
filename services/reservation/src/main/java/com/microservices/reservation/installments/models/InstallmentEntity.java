package com.microservices.reservation.installments.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_installment")
public class InstallmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "installment_id", columnDefinition = "uuid", unique = true, nullable = false, updatable = false)
    private UUID uid;

    @Column(name = "value", columnDefinition = "numeric", nullable = false, updatable = false)
    private Double value;

    @Column(name = "payment_due", columnDefinition = "timestamp without time zone", nullable = false, updatable = false)
    private LocalDateTime paymentDue;

    @Column(name = "paid", columnDefinition = "boolean", nullable = false)
    private boolean paid;

    @Column(name = "debtor", columnDefinition = "uuid", nullable = false, updatable = false)
    private UUID debtor;

    @Column(name = "creditor", columnDefinition = "uuid", nullable = false, updatable = false)
    private UUID creditor;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "timestamp without time zone", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at", columnDefinition = "timestamp without time zone", insertable = false)
    private LocalDateTime modifiedAt;
}
