package com.hamsaye.chat.applications.responses;

import org.springframework.stereotype.Component;

/**
 * Component that defines various response message types for standardized responses across the application.
 *
 * <p>This class provides predefined {@link ResponseFormat} instances representing different response types
 * such as success, error conditions, and specific application errors. Each response type includes a code and
 * a message for consistency in API responses.</p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 * @since 1.0
 */
@Component
public class ResponseMessageType {

    /**
     * Represents a successful operation.
     */
    public static final ResponseFormat SUCCESS = ResponseFormat.builder()
            .code(0)
            .message("successful")
            .build();

    /**
     * Represents an internal server error.
     */
    public static final ResponseFormat INTERNAL = ResponseFormat.builder()
            .code(500)
            .message("internal server error is occurred")
            .build();

    /**
     * Represents a situation where a user is not found.
     */
    public static final ResponseFormat USER_NOT_FOUND = ResponseFormat.builder()
            .code(14002)
            .message("user not found")
            .build();

    /**
     * Represents a situation where a chat message is not found.
     */
    public static final ResponseFormat CHAT_MESSAGE_NOT_FOUND = ResponseFormat.builder()
            .code(14050)
            .message("chat message not found")
            .build();

    /**
     * Represents a situation where a chat conversation is not found.
     */
    public static final ResponseFormat CHAT_CONVERSATION_NOT_FOUND = ResponseFormat.builder()
            .code(14051)
            .message("chat conversation not found")
            .build();

    /**
     * Represents a situation where a chat conversation already exists.
     */
    public static final ResponseFormat CHAT_CONVERSATION_ALREADY_EXISTS = ResponseFormat.builder()
            .code(14052)
            .message("chat conversation already exists")
            .build();

    /**
     * Represents a situation where a scope is not found.
     */
    public static final ResponseFormat SCOPE_NOT_FOUND = ResponseFormat.builder()
            .code(14001)
            .message("scope not found")
            .build();

    /**
     * Represents a situation where user information cannot be registered.
     */
    public static final ResponseFormat PERSIST_USER = ResponseFormat.builder()
            .code(16050)
            .message("user information cannot be registered")
            .build();

    /**
     * Represents a situation where a token is expired.
     */
    public static final ResponseFormat EXPIRED_TOKEN = ResponseFormat.builder()
            .code(10020)
            .message("token is expired")
            .build();

    /**
     * Represents a situation where authentication credentials are not found.
     */
    public static final ResponseFormat AUTHENTICATION_CREDENTIAL_NOT_FOUND = ResponseFormat.builder()
            .code(10050)
            .message("authentication credential not found")
            .build();
}
