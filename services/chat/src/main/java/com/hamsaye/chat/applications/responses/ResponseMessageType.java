package com.hamsaye.chat.applications.responses;


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

    public static final ResponseFormat USER_NOT_FOUND = ResponseFormat.builder()
            .code(14002)
            .message("user not found")
            .build();

    public static final ResponseFormat CHAT_MESSAGE_NOT_FOUND = ResponseFormat.builder()
            .code(14050)
            .message("chat message not found")
            .build();

    public static final ResponseFormat CHAT_CONVERSATION_NOT_FOUND = ResponseFormat.builder()
            .code(14051)
            .message("chat conversation not found")
            .build();

    public static final ResponseFormat CHAT_CONVERSATION_ALREADY_EXISTS = ResponseFormat.builder()
            .code(14052)
            .message("chat conversation already exists")
            .build();

    public static final ResponseFormat SCOPE_NOT_FOUND = ResponseFormat.builder()
            .code(14001)
            .message("scope not found")
            .build();

    public static final ResponseFormat PERSIST_USER = ResponseFormat.builder()
            .code(16050)
            .message("user information cannot be registered")
            .build();

    public static final ResponseFormat EXPIRED_TOKEN = ResponseFormat.builder()
            .code(10020)
            .message("token is expired")
            .build();

    public static final ResponseFormat AUTHENTICATION_CREDENTIAL_NOT_FOUND = ResponseFormat.builder()
            .code(10050)
            .message("authentication credential not found")
            .build();

}
