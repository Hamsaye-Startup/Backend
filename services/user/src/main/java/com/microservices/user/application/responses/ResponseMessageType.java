package com.microservices.user.application.responses;


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

    public static final ResponseFormat USER_NOT_FOUND = ResponseFormat.builder()
            .code(14002)
            .message("user not found")
            .build();

    public static final ResponseFormat ROLE_NOT_FOUND = ResponseFormat.builder()
            .code(14003)
            .message("role not found")
            .build();

    public static final ResponseFormat CUSTOMER_NOT_FOUND = ResponseFormat.builder()
            .code(14004)
            .message("customer not found")
            .build();

    public static final ResponseFormat PERSIST_USER = ResponseFormat.builder()
            .code(16050)
            .message("user information cannot be registered")
            .build();

    public static final ResponseFormat PERSIST_ROLE = ResponseFormat.builder()
            .code(16051)
            .message("role information cannot be registered")
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
