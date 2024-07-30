package com.microservices.warehouse.applications.responses;


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

    public static final ResponseFormat FEATURE_NOT_FOUND = ResponseFormat.builder()
            .code(14005)
            .message("feature not found")
            .build();

    public static final ResponseFormat POLICY_NOT_FOUND = ResponseFormat.builder()
            .code(14006)
            .message("policy not found")
            .build();

    public static final ResponseFormat WAREHOUSE_NOT_FOUND = ResponseFormat.builder()
            .code(14007)
            .message("warehouse not found")
            .build();

    public static final ResponseFormat BOOKMARK_NOT_FOUND = ResponseFormat.builder()
            .code(14008)
            .message("bookmark not found")
            .build();

    public static final ResponseFormat PERSIST_FEATURE = ResponseFormat.builder()
            .code(16053)
            .message("feature information cannot be registered")
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

}
