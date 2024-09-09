package com.microservices.reservation.installments.responses;

import lombok.Builder;

import java.util.Set;

/**
 * Represents a response containing details about a collection of installments.
 *
 * The {@code InstallmentFactor} class is used to encapsulate a set of installment responses
 * along with a total count. It is typically used to provide a comprehensive view of multiple
 * installment entities in response to a query or request.
 *
 * @param installments A set of installment responses that details individual installments.
 * @param total The total count of installments present in the response.
 *
 * @since 1.0
 * @version 1.0
 */
@Builder
public record InstallmentFactor(
        Set<InstallmentResponse> installments,
        int total
) {
}
