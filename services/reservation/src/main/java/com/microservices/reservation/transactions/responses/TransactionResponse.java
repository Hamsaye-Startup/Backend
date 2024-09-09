package com.microservices.reservation.transactions.responses;

import com.microservices.reservation.installments.responses.InstallmentResponse;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * This record represents a response containing transaction details.
 * It includes information such as transaction type, code, associated installment,
 * site fees, discount details, and transfer timestamp.
 *
 * @param id               The unique identifier for the transaction.
 * @param type             The type of the transaction.
 * @param code             A unique code associated with the transaction.
 * @param installment      The response containing details about the associated installment.
 * @param siteFees         The fees charged by the site for this transaction.
 * @param discountPercent  The discount percentage applied to the transaction.
 * @param discountCode     A code representing the discount applied.
 * @param transferOn       The timestamp indicating when the transfer occurred.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Builder
public record TransactionResponse(
        Long id,
        String type,
        String code,
        InstallmentResponse installment,
        Double siteFees,
        Double discountPercent,
        String discountCode,
        LocalDateTime transferOn
) {
}
