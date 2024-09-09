package com.microservices.reservation.installments.responses;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a response containing details about a single installment.
 *
 * The {@code InstallmentResponse} class is used to encapsulate the details of an installment
 * entity. It provides information such as the unique identifier, value, due date, payment status,
 * and the involved debtor and creditor.
 *
 * @param uid The unique identifier of the installment.
 * @param value The amount of the installment.
 * @param paymentDue The due date for the installment payment.
 * @param paid Indicates whether the installment has been paid.
 * @param debtor The unique identifier of the debtor responsible for the installment.
 * @param creditor The unique identifier of the creditor receiving the installment.
 *
 * @since 1.0
 * @version 1.0
 */
@Builder
public record InstallmentResponse(
        UUID uid,
        Double value,
        LocalDateTime paymentDue,
        boolean paid,
        UUID debtor,
        UUID creditor
) {
}
