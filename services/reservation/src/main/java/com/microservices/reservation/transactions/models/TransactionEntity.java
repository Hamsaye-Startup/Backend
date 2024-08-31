package com.microservices.reservation.transactions.models;

import com.microservices.reservation.installments.models.InstallmentEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

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

    @Column(name = "type", columnDefinition = "character varying", nullable = false, updatable = false)
    private String type;

    @Column(name = "code", columnDefinition = "character varying", nullable = false, updatable = false)
    private String code;

    @OneToOne
    @JoinColumn(name = "installment", unique = true, nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_installment_transaction"))
    private InstallmentEntity installment;

    @Column(name = "site_fees", columnDefinition = "numeric", nullable = false, updatable = false)
    private Double siteFees;

    @Column(name = "discount_amt", columnDefinition = "numeric", nullable = false, updatable = false)
    private Double discountAmount;

    @Column(name = "transfer_on", columnDefinition = "timestamp without time zone", nullable = false, updatable = false)
    private LocalDateTime transferOn;
}
