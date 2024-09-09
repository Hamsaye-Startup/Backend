package com.microservices.reservation.transactions.services;

import com.microservices.reservation.transactions.exceptions.NotFoundTransactionException;
import com.microservices.reservation.transactions.exceptions.PersistTransactionException;
import com.microservices.reservation.transactions.models.TransactionEntity;
import com.microservices.reservation.transactions.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing transaction entities, including persisting and retrieving transactions.
 * This service handles interactions with the transaction repository and manages transaction-related exceptions.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;

    /**
     * Persists a transaction entity to the database.
     *
     * @param transaction The {@link TransactionEntity} to be persisted.
     * @return The persisted {@link TransactionEntity}.
     * @throws PersistTransactionException If an error occurs while persisting the transaction.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public TransactionEntity persist(TransactionEntity transaction) {
        try {
            return repository.saveAndFlush(transaction);
        } catch (RuntimeException ex) {
            throw new PersistTransactionException(
                    ex.getCause(),
                    transaction.getInstallment().getUid().toString()
            );
        }
    }

    /**
     * Retrieves a transaction entity by its ID.
     *
     * @param id The ID of the transaction to retrieve.
     * @return The {@link TransactionEntity} with the specified ID.
     * @throws NotFoundTransactionException If no transaction with the given ID is found.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public TransactionEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundTransactionException(id.toString()));
    }
}
