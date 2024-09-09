package com.microservices.reservation.transactions.controllers;

import com.microservices.reservation.applications.mapper.MessageMapper;
import com.microservices.reservation.transactions.responses.TransactionResponse;
import com.microservices.reservation.transactions.services.TransactionServiceManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * This class is a REST controller for managing transactions.
 * It provides endpoints to add transactions and retrieve transaction details.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@RestController
@RequestMapping("api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {

    /**
     * @see com.microservices.reservation.applications.mapper.MessageMapper
     */
    private final MessageMapper mapper;

    /**
     * @see com.microservices.reservation.transactions.services.TransactionServiceManagement
     */
    private final TransactionServiceManagement management;

    /**
     * Adds a transaction based on the provided installment ID.
     *
     * @param uid The UUID of the installment for which the transaction is to be created.
     * @return A {@link ResponseEntity} containing the {@link TransactionResponse} for the created transaction.
     * @since 1.0
     */
    @PostMapping("/installment/{id}")
    public ResponseEntity<?> add(@PathVariable("id") UUID uid) {
        TransactionResponse response = management.persist(uid);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    /**
     * Retrieves a transaction by its ID.
     *
     * @param id The ID of the transaction to retrieve.
     * @return A {@link ResponseEntity} containing the {@link TransactionResponse} for the requested transaction.
     * @since 1.0
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<?> showTransactionById(@PathVariable("id") Long id) {
        TransactionResponse response = management.findTransactionById(id);
        return ResponseEntity.ok(mapper.toResponse(response));
    }
}
