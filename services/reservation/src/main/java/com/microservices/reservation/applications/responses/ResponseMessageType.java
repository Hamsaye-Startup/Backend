package com.microservices.reservation.applications.responses;

import org.springframework.stereotype.Component;

/**
 * Component class that defines various response message types used throughout the application.
 *
 * <p>This class provides a collection of predefined response formats that include common success and error messages,
 * each associated with a unique code. These formats can be used for consistent response handling and error reporting.</p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 * @since 1.0
 */
@Component
public class ResponseMessageType {

    /**
     * Success response format.
     *
     * @see ResponseFormat
     */
    public static final ResponseFormat SUCCESS = ResponseFormat.builder()
            .code(0)
            .message("successful")
            .build();

    /**
     * Internal server error response format.
     *
     * @see ResponseFormat
     */
    public static final ResponseFormat INTERNAL = ResponseFormat.builder()
            .code(500)
            .message("internal server error is occurred")
            .build();

    /**
     * Reservation not found response format.
     *
     * @see ResponseFormat
     */
    public static final ResponseFormat RESERVATION_NOT_FOUND = ResponseFormat.builder()
            .code(14009)
            .message("reservation not found")
            .build();

    /**
     * Installment not found response format.
     *
     * @see ResponseFormat
     */
    public static final ResponseFormat INSTALLMENT_NOT_FOUND = ResponseFormat.builder()
            .code(14010)
            .message("installment not found")
            .build();

    /**
     * Transaction not found response format.
     *
     * @see ResponseFormat
     */
    public static final ResponseFormat TRANSACTION_NOT_FOUND = ResponseFormat.builder()
            .code(14011)
            .message("transaction not found")
            .build();

    /**
     * Product not found response format.
     *
     * @see ResponseFormat
     */
    public static final ResponseFormat PRODUCT_NOT_FOUND = ResponseFormat.builder()
            .code(14012)
            .message("product not found")
            .build();

    /**
     * Product type not found response format.
     *
     * @see ResponseFormat
     */
    public static final ResponseFormat PRODUCT_TYPE_NOT_FOUND = ResponseFormat.builder()
            .code(14013)
            .message("product type not found")
            .build();

    /**
     * Persist reservation failure response format.
     *
     * @see ResponseFormat
     */
    public static final ResponseFormat PERSIST_RESERVATION = ResponseFormat.builder()
            .code(16055)
            .message("reservation cannot be inserted")
            .build();

    /**
     * Persist transaction failure response format.
     *
     * @see ResponseFormat
     */
    public static final ResponseFormat PERSIST_TRANSACTION = ResponseFormat.builder()
            .code(16056)
            .message("transaction cannot be inserted")
            .build();

    /**
     * Persist product failure response format.
     *
     * @see ResponseFormat
     */
    public static final ResponseFormat PERSIST_PRODUCT = ResponseFormat.builder()
            .code(16057)
            .message("product cannot be inserted")
            .build();

    /**
     * Persist installment failure response format.
     *
     * @see ResponseFormat
     */
    public static final ResponseFormat PERSIST_INSTALLMENT = ResponseFormat.builder()
            .code(16058)
            .message("installment cannot be inserted")
            .build();

    /**
     * Authentication credential not found response format.
     *
     * @see ResponseFormat
     */
    public static final ResponseFormat AUTHENTICATION_CREDENTIAL_NOT_FOUND = ResponseFormat.builder()
            .code(10050)
            .message("authentication credential not found")
            .build();

    /**
     * Total installment out of duration response format.
     *
     * @see ResponseFormat
     */
    public static final ResponseFormat TOTAL_INSTALLMENT = ResponseFormat.builder()
            .code(12006)
            .message("total installments number is out of duration")
            .build();
}
