package com.microservices.reservation.transactions.mappers;

import com.microservices.reservation.installments.mappers.InstallmentMapper;
import com.microservices.reservation.transactions.models.TransactionEntity;
import com.microservices.reservation.transactions.responses.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionMapper {

    private final InstallmentMapper mapper;

    public TransactionResponse toResponse(TransactionEntity transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .type(transaction.getType())
                .code(transaction.getCode())
                .installment(mapper.toResponse(transaction.getInstallment()))
                .siteFees(transaction.getSiteFees())
                .discountPercent(0D) // TODO: discount percent gets from the Discount Entity
                .discountCode("DISCOUNT CODE") // TODO: discount code gets from the Discount Entity
                .transferOn(transaction.getTransferOn())
                .build();
    }
}
