package com.microservices.reservation.transactions.models;

import com.microservices.reservation.installments.models.InstallmentEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * This class represents a transaction entity within the reservation system.
 * It maps to the {@code tb_transaction} table in the database and contains information
 * about a transaction such as type, code, associated installment, site fees, and discount amount.
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
@Table(name = "tb_transaction")
@SequenceGenerator(name = "tb_transaction_seq", sequenceName = "tb_transaction_seq", allocationSize = 6, initialValue = 1001)
public class TransactionEntity implements Serializable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tb_transaction_seq"
    )
    @Column(name = "transaction_id", columnDefinition = "bigint", unique = true, nullable = false, updatable = false)
    private Long id;

    /**
     * The type of the transaction.
     */
    @Column(name = "type", columnDefinition = "character varying", nullable = false, updatable = false)
    private String type;

    /**
     * A unique code associated with the transaction.
     */
    @Column(name = "code", columnDefinition = "character varying", nullable = false, updatable = false)
    private String code;

    /**
     * The installment associated with this transaction.
     * {@link InstallmentEntity} is used for the detailed information about the installment.
     */
    @OneToOne
    @JoinColumn(name = "installment", unique = true, nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_installment_transaction"))
    private InstallmentEntity installment;

    /**
     * The fees charged by the site for this transaction.
     */
    @Column(name = "site_fees", columnDefinition = "numeric", nullable = false, updatable = false)
    private Double siteFees;

    @Column(name = "discount_amt", columnDefinition = "numeric", nullable = false, updatable = false)
    private Double discountAmount;

    @Column(name = "transfer_on", columnDefinition = "timestamp without time zone", nullable = false, updatable = false)
    private LocalDateTime transferOn;
}
