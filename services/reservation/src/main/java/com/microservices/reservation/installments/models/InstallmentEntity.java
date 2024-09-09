package com.microservices.reservation.installments.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents an installment entity within the system.
 *
 * An installment entity stores details about a financial installment, including
 * the value of the installment, its payment due date, and whether it has been paid.
 * It also tracks the debtor and creditor associated with the installment, as well
 * as timestamps for creation and modification.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
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

    /**
     * The value of the installment.
     */
    @Column(name = "value", columnDefinition = "numeric", nullable = false, updatable = false)
    private Double value;

    /**
     * The due date for the payment of the installment.
     */
    @Column(name = "payment_due", columnDefinition = "timestamp without time zone", nullable = false, updatable = false)
    private LocalDateTime paymentDue;

    /**
     * Indicates whether the installment has been paid.
     */
    @Column(name = "paid", columnDefinition = "boolean", nullable = false)
    private boolean paid;

    /**
     * Unique identifier for the debtor associated with the installment.
     */
    @Column(name = "debtor", columnDefinition = "uuid", nullable = false, updatable = false)
    private UUID debtor;

    /**
     * Unique identifier for the creditor associated with the installment.
     */
    @Column(name = "creditor", columnDefinition = "uuid", nullable = false, updatable = false)
    private UUID creditor;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "timestamp without time zone", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at", columnDefinition = "timestamp without time zone", insertable = false)
    private LocalDateTime modifiedAt;
}
