package com.microservices.reservation.transactions.controllers;

import com.microservices.reservation.applications.mapper.MessageMapper;
import com.microservices.reservation.transactions.responses.TransactionResponse;
import com.microservices.reservation.transactions.services.TransactionServiceManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final MessageMapper mapper;
    private final TransactionServiceManagement management;

    @PostMapping("/installment/{id}")
    public ResponseEntity<?> add(@PathVariable("id") UUID uid) {
        TransactionResponse response = management.persist(uid);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> showTransactionById(@PathVariable("id") Long id) {
        TransactionResponse response = management.findTransactionById(id);
        return ResponseEntity.ok(mapper.toResponse(response));
    }
}
