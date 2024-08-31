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

@Service
@RequiredArgsConstructor
public class TransactionServiceManagement {

    private final TransactionMapper mapper;
    private final TransactionService service;

    private final InstallmentService installmentService;

    public TransactionResponse persist(UUID uid) {

        // check the installment
        InstallmentEntity installment = installmentService.findById(uid);

        // TODO: tracking number is the id
        // generate a transaction with tracking number
        TransactionEntity transaction = TransactionEntity.builder()
                .code("CODE") // TODO: generate the code
                .type("TYPE") // TODO: generate the type for transaction
                .installment(installment)
                .siteFees(installment.getValue() * RentWarehousePolicy.siteFeePercent)
                .discountAmount(0D) // TODO: generate the discount code api
                .transferOn(LocalDateTime.now())
                .build();

        TransactionEntity persisted = service.persist(transaction);

        // update the installment
        installment.setPaid(true);
        InstallmentEntity updated = installmentService.persist(installment);

        persisted.setInstallment(updated);
        return mapper.toResponse(persisted);
    }

    public TransactionResponse findTransactionById(Long id) {
        return mapper.toResponse(service.findById(id));
    }
}
