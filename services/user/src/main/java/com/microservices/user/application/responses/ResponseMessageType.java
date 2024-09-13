package com.microservices.user.application.responses;

import org.springframework.stereotype.Component;

/**
 * A component class that defines various {@link ResponseFormat} constants for standard response messages.
 *
 * <p>This class contains predefined response formats for different scenarios including success, errors,
 * and various specific cases like user not found or token expiration.</p>
 *
 * <p>The constants provided in this class can be used throughout the application to ensure consistency
 * in response messages and codes.</p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 * @since 1.0
 */
@Component
public class ResponseMessageType {

    /**
     * Response format for a successful operation.
     */
    public static final ResponseFormat SUCCESS = ResponseFormat.builder()
            .code(0)
            .message("successful")
            .build();

    /**
     * Response format for an internal server error.
     */
    public static final ResponseFormat INTERNAL = ResponseFormat.builder()
            .code(500)
            .message("internal server error is occurred")
            .build();

    /**
     * Response format for a user not found error.
     */
    public static final ResponseFormat USER_NOT_FOUND = ResponseFormat.builder()
            .code(14002)
            .message("user not found")
            .build();

    /**
     * Response format for a role not found error.
     */
    public static final ResponseFormat ROLE_NOT_FOUND = ResponseFormat.builder()
            .code(14003)
            .message("role not found")
            .build();

    /**
     * Response format for a customer not found error.
     */
    public static final ResponseFormat USER_DETAIL_NOT_FOUND = ResponseFormat.builder()
            .code(14004)
            .message("user detail information not found")
            .build();

    /**
     * Response format for a failure to persist user information.
     */
    public static final ResponseFormat PERSIST_USER = ResponseFormat.builder()
            .code(16050)
            .message("user information cannot be registered")
            .build();

    /**
     * Response format for a failure to persist role information.
     */
    public static final ResponseFormat PERSIST_ROLE = ResponseFormat.builder()
            .code(16051)
            .message("role information cannot be registered")
            .build();

    /**
     * Response format for a failure to persist customer information.
     */
    public static final ResponseFormat PERSIST_USER_DETAIL = ResponseFormat.builder()
            .code(16052)
            .message("user detail information cannot be registered")
            .build();

    /**
     * Response format for an illegal request.
     */
    public static final ResponseFormat ILLEGAL_REQUEST = ResponseFormat.builder()
            .code(-1)
            .message("illegal request is detected")
            .build();

    /**
     * Response format for an expired token error.
     */
    public static final ResponseFormat EXPIRED_TOKEN = ResponseFormat.builder()
            .code(10020)
            .message("token is expired")
            .build();

    /**
     * Response format for missing authentication credentials.
     */
    public static final ResponseFormat AUTHENTICATION_CREDENTIAL_NOT_FOUND = ResponseFormat.builder()
            .code(10050)
            .message("authentication credential not found")
            .build();
}
