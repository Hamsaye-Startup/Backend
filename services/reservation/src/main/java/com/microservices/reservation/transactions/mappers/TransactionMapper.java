package com.microservices.reservation.transactions.mappers;

import com.microservices.reservation.installments.mappers.InstallmentMapper;
import com.microservices.reservation.transactions.models.TransactionEntity;
import com.microservices.reservation.transactions.responses.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * This class is responsible for mapping {@link TransactionEntity} instances to {@link TransactionResponse} instances.
 * It uses an {@link InstallmentMapper} to convert installment details.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class TransactionMapper {

    private final InstallmentMapper mapper;

    /**
     * Converts a {@link TransactionEntity} to a {@link TransactionResponse}.
     * This method maps entity fields to response fields and uses the {@link InstallmentMapper}
     * to convert the installment details.
     *
     * @param transaction The {@link TransactionEntity} to convert.
     * @return A {@link TransactionResponse} containing the mapped data from the entity.
     */
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
