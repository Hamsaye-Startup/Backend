package com.microservices.reservation.installments.services;

import com.microservices.reservation.installments.exceptions.PersistInstallmentException;
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

/**
 * Service class for handling operations related to installments.
 * Provides methods to generate, calculate, and persist installments,
 * as well as retrieve them by their unique identifier.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class InstallmentService {

    /**
     * @see com.microservices.reservation.installments.repositories.InstallmentRepository
     */
    private final InstallmentRepository repository;

    /**
     * Generates an installment with a specified amount, creditor, and debtor.
     * The payment due date is set to two hours from the current time.
     *
     * @param amount The amount for the installment.
     * @param creditor The UUID of the creditor.
     * @param debtor The UUID of the debtor.
     * @return An {@link InstallmentEntity} with the specified details.
     * @since 1.0
     */
    public InstallmentEntity generate(Double amount, UUID creditor, UUID debtor) {
        return InstallmentEntity.builder()
                .value(amount)
                .debtor(debtor)
                .creditor(creditor)
                .paymentDue(LocalDateTime.now().plusHours(2))
                .paid(false)
                .build();
    }

    /**
     * Generates an installment with a specified amount, creditor, debtor, and due date.
     *
     * @param amount The amount for the installment.
     * @param creditor The UUID of the creditor.
     * @param debtor The UUID of the debtor.
     * @param due The payment due date.
     * @return An {@link InstallmentEntity} with the specified details.
     * @since 1.0
     */
    public InstallmentEntity generate(Double amount, UUID creditor, UUID debtor, LocalDateTime due) {
        return InstallmentEntity.builder()
                .value(amount)
                .debtor(debtor)
                .creditor(creditor)
                .paymentDue(due)
                .paid(false)
                .build();
    }

    /**
     * Calculates a set of installments for each month between two dates.
     *
     * @param amount The amount for each installment.
     * @param creditor The UUID of the creditor.
     * @param debtor The UUID of the debtor.
     * @param from The start date for the calculation.
     * @param to The end date for the calculation.
     * @return A set of {@link InstallmentEntity} objects, each representing an installment for a month.
     * @since 1.0
     */
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

    /**
     * Finds an installment by its unique identifier.
     *
     * @param uid The unique identifier of the installment.
     * @return The {@link InstallmentEntity} with the specified identifier.
     * @throws NotFoundInstallmentException if no installment with the specified ID is found.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public InstallmentEntity findById(UUID uid) {
        return repository.findById(uid)
                .orElseThrow(() -> new NotFoundInstallmentException(uid.toString()));
    }

    /**
     * Persists an installment entity to the database.
     *
     * @param installment The {@link InstallmentEntity} to be persisted.
     * @return The persisted {@link InstallmentEntity}.
     * @throws PersistInstallmentException if an error occurs during persistence.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public InstallmentEntity persist(InstallmentEntity installment) {
        try {
            return repository.saveAndFlush(installment);
        } catch (RuntimeException ex) {
            throw new PersistInstallmentException(ex.getCause(), installment.getDebtor().toString());
        }
    }
}
