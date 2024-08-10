package com.microservices.reservation.transactions.services;

import com.microservices.reservation.transactions.exceptions.NotFoundTrasactionException;
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
        return repository.saveAndFlush(transaction);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public TransactionEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundTrasactionException(id.toString()));
    }
}
