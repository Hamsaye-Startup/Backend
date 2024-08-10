package com.microservices.reservation.installments.services;

import com.microservices.reservation.installments.models.InstallmentEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class InstallmentService {

    public InstallmentEntity persist(Double amount, UUID creditor, UUID debtor) {
        return InstallmentEntity.builder()
                .value(amount)
                .debtor(debtor)
                .creditor(creditor)
                .paymentDue(LocalDateTime.now().plusHours(2))
                .paid(false)
                .build();
    }

    public InstallmentEntity persist(Double amount, UUID creditor, UUID debtor, LocalDateTime due) {
        return InstallmentEntity.builder()
                .value(amount)
                .debtor(debtor)
                .creditor(creditor)
                .paymentDue(due)
                .paid(false)
                .build();
    }

    public Set<InstallmentEntity> calculatePerMonth(Double amount, UUID creditor, UUID debtor, LocalDate from, LocalDate to) {

        // calculate the months between from and to dates
        Period period = Period.between(from, to);
        int months = period.getMonths();

        // generate installment chain
        Set<InstallmentEntity> installments = new HashSet<>();
        for (int index = 0; index < months; index++) {

            InstallmentEntity installment = persist(
                    amount,
                    creditor,
                    debtor,
                    LocalDateTime.now().plusHours(2).plusMonths(index)
            );
            installments.add(installment);
        }

        return installments;
    }
}
