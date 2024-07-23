package com.hamsaye.customer.application.responses;


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

    public static final ResponseFormat CUSTOMER_NOT_FOUND = ResponseFormat.builder()
            .code(14004)
            .message("customer not found")
            .build();

    public static final ResponseFormat PERSIST_CUSTOMER = ResponseFormat.builder()
            .code(16052)
            .message("customer information cannot be registered")
            .build();

    public static final ResponseFormat ILLEGAL_REQUEST = ResponseFormat.builder()
            .code(-1)
            .message("illegal request is detected")
            .build();

    public static final ResponseFormat EXPIRED_TOKEN = ResponseFormat.builder()
            .code(10020)
            .message("token is expired")
            .build();

}
