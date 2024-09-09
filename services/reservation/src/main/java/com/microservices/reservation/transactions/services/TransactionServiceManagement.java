package com.microservices.reservation.transactions.services;

import com.microservices.reservation.installments.models.InstallmentEntity;
import com.microservices.reservation.installments.services.InstallmentService;
import com.microservices.reservation.policies.RentWarehousePolicy;
import com.microservices.reservation.transactions.mappers.TransactionMapper;
import com.microservices.reservation.transactions.models.TransactionEntity;
import com.microservices.reservation.transactions.responses.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * This service manages transactions, including creating and retrieving transaction records.
 * It interacts with installment services to process transactions and update installment statuses.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class TransactionServiceManagement {

    /**
     * @see com.microservices.reservation.transactions.mappers.TransactionMapper
     */
    private final TransactionMapper mapper;

    /**
     * @see com.microservices.reservation.transactions.services.TransactionService
     */
    private final TransactionService service;

    /**
     * @see com.microservices.reservation.installments.services.InstallmentService
     */
    private final InstallmentService installmentService;

    /**
     * Creates and persists a new transaction based on the provided installment ID.
     * Updates the status of the associated installment to paid.
     *
     * @param uid The UUID of the installment for which the transaction is to be created.
     * @return A {@link TransactionResponse} containing details of the created transaction.
     * @since 1.0
     */
    public TransactionResponse persist(UUID uid) {

        // Check the installment
        InstallmentEntity installment = installmentService.findById(uid);

        // TODO: Generate a transaction with appropriate code and type
        TransactionEntity transaction = TransactionEntity.builder()
                .code("CODE") // TODO: generate the code
                .type("TYPE") // TODO: generate the type for transaction
                .installment(installment)
                .siteFees(installment.getValue() * RentWarehousePolicy.siteFeePercent)
                .discountAmount(0D) // TODO: generate the discount amount
                .transferOn(LocalDateTime.now())
                .build();

        TransactionEntity persisted = service.persist(transaction);

        // Update the installment status
        installment.setPaid(true);
        InstallmentEntity updated = installmentService.persist(installment);

        persisted.setInstallment(updated);
        return mapper.toResponse(persisted);
    }

    /**
     * Retrieves a transaction by its ID.
     *
     * @param id The ID of the transaction to retrieve.
     * @return A {@link TransactionResponse} containing details of the requested transaction.
     * @since 1.0
     */
    public TransactionResponse findTransactionById(Long id) {
        return mapper.toResponse(service.findById(id));
    }
}
