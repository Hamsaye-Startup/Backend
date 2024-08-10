package com.microservices.reservation.installments.mappers;

import com.microservices.reservation.installments.models.InstallmentEntity;
import com.microservices.reservation.installments.responses.InstallmentFactor;
import com.microservices.reservation.installments.responses.InstallmentResponse;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class InstallmentMapper {

    public InstallmentResponse toResponse(InstallmentEntity installment) {
        return InstallmentResponse.builder()
                .uid(installment.getUid())
                .creditor(installment.getCreditor())
                .debtor(installment.getDebtor())
                .paid(installment.isPaid())
                .paymentDue(installment.getPaymentDue())
                .build();
    }

    public InstallmentFactor toFactor(InstallmentResponse response) {
        return InstallmentFactor.builder()
                .installments(Set.of(response))
                .total(1)
                .build();
    }

    public InstallmentFactor toFactor(Set<InstallmentResponse> responses) {
        return InstallmentFactor.builder()
                .installments(responses)
                .total(responses.size())
                .build();
    }
}
