package com.microservices.reservation.applications.responses;


import org.springframework.stereotype.Component;

@Component
public class ResponseMessageType {

    public static final ResponseFormat SUCCESS = ResponseFormat.builder()
            .code(0)
            .message("successful")
            .build();

    public static final ResponseFormat INTERNAL = ResponseFormat.builder()
            .code(500)
            .message("internal server error is occurred")
            .build();

    public static final ResponseFormat SCOPE_NOT_FOUND = ResponseFormat.builder()
            .code(14001)
            .message("scope not found")
            .build();

    public static final ResponseFormat RESERVATION_NOT_FOUND = ResponseFormat.builder()
            .code(14009)
            .message("reservation not found")
            .build();

    public static final ResponseFormat INSTALLMENT_NOT_FOUND = ResponseFormat.builder()
            .code(14010)
            .message("installment not found")
            .build();

    public static final ResponseFormat TRANSACTION_NOT_FOUND = ResponseFormat.builder()
            .code(14011)
            .message("transaction not found")
            .build();

    public static final ResponseFormat PRODUCT_NOT_FOUND = ResponseFormat.builder()
            .code(14012)
            .message("product not found")
            .build();

    public static final ResponseFormat PRODUCT_TYPE_NOT_FOUND = ResponseFormat.builder()
            .code(14013)
            .message("product type not found")
            .build();

    public static final ResponseFormat PERSIST_WAREHOUSE = ResponseFormat.builder()
            .code(16054)
            .message("warehouse information cannot be registered")
            .build();

    public static final ResponseFormat ILLEGAL_REQUEST = ResponseFormat.builder()
            .code(-1)
            .message("illegal request is detected")
            .build();

    public static final ResponseFormat EXPIRED_TOKEN = ResponseFormat.builder()
            .code(10020)
            .message("token is expired")
            .build();

    public static final ResponseFormat TOTAL_INSTALLMENT = ResponseFormat.builder()
            .code(12006)
            .message("total installments number is out of duration")
            .build();

}
