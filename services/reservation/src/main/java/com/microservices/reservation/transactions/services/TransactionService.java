package com.microservices.reservation.transactions.services;

import com.microservices.reservation.transactions.exceptions.NotFoundTransactionException;
import com.microservices.reservation.transactions.exceptions.PersistTransactionException;
import com.microservices.reservation.transactions.models.TransactionEntity;
import com.microservices.reservation.transactions.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;

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

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public TransactionEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundTransactionException(id.toString()));
    }
}
