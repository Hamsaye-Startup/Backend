package com.microservices.reservation.installments.mappers;

import com.microservices.reservation.installments.models.InstallmentEntity;
import com.microservices.reservation.installments.responses.InstallmentFactor;
import com.microservices.reservation.installments.responses.InstallmentResponse;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Provides mapping methods between {@link InstallmentEntity} and {@link InstallmentResponse}
 * as well as methods to build {@link InstallmentFactor} objects.
 *
 * The {@code InstallmentMapper} class is responsible for converting between entity and response
 * objects for installments. It facilitates the transformation of data for use in different layers
 * of the application, such as converting entities retrieved from a database into response objects
 * for use in API responses.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
public class InstallmentMapper {

    /**
     * Converts an {@link InstallmentEntity} to an {@link InstallmentResponse}.
     *
     * @param installment The {@link InstallmentEntity} to be converted.
     * @return An {@link InstallmentResponse} containing the details of the installment.
     */
    public InstallmentResponse toResponse(InstallmentEntity installment) {
        return InstallmentResponse.builder()
                .uid(installment.getUid())
                .creditor(installment.getCreditor())
                .debtor(installment.getDebtor())
                .paid(installment.isPaid())
                .paymentDue(installment.getPaymentDue())
                .build();
    }

    /**
     * Creates an {@link InstallmentFactor} from a single {@link InstallmentResponse}.
     *
     * @param response The {@link InstallmentResponse} to be included in the factor.
     * @return An {@link InstallmentFactor} with the given response and a total count of 1.
     * @since 1.0
     */
    public InstallmentFactor toFactor(InstallmentResponse response) {
        return InstallmentFactor.builder()
                .installments(Set.of(response))
                .total(1)
                .build();
    }

    /**
     * Creates an {@link InstallmentFactor} from a set of {@link InstallmentResponse} objects.
     *
     * @param responses The set of {@link InstallmentResponse} objects to be included in the factor.
     * @return An {@link InstallmentFactor} with the given responses and a total count equal to the size of the set.
     * @since 1.0
     */
    public InstallmentFactor toFactor(Set<InstallmentResponse> responses) {
        return InstallmentFactor.builder()
                .installments(responses)
                .total(responses.size())
                .build();
    }
}
