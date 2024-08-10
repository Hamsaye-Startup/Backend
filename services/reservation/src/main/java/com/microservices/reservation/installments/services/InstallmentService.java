package com.microservices.reservation.installments.services;

import com.microservices.reservation.installments.models.InstallmentEntity;
import com.microservices.reservation.installments.repositories.InstallmentRepository;
import com.microservices.reservation.installments.exceptions.NotFoundInstallmentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InstallmentService {

    private final InstallmentRepository repository;

    public InstallmentEntity generate(Double amount, UUID creditor, UUID debtor) {
        return InstallmentEntity.builder()
                .value(amount)
                .debtor(debtor)
                .creditor(creditor)
                .paymentDue(LocalDateTime.now().plusHours(2))
                .paid(false)
                .build();
    }

    public InstallmentEntity generate(Double amount, UUID creditor, UUID debtor, LocalDateTime due) {
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

            InstallmentEntity installment = generate(
                    amount,
                    creditor,
                    debtor,
                    LocalDateTime.now().plusHours(2).plusMonths(index)
            );
            installments.add(installment);
        }

        return installments;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public InstallmentEntity findById(UUID uid) {
        return repository.findById(uid)
                .orElseThrow(() -> new NotFoundInstallmentException(uid.toString()));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public InstallmentEntity persist(InstallmentEntity installment) {
        return repository.saveAndFlush(installment);
    }
}
