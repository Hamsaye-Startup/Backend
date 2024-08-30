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

    public static final ResponseFormat STORAGE_NOT_FOUND = ResponseFormat.builder()
            .code(14007)
            .message("storage not found")
            .build();

    public static final ResponseFormat BOOKMARK_NOT_FOUND = ResponseFormat.builder()
            .code(14008)
            .message("bookmark not found")
            .build();

    public static final ResponseFormat RESERVATION_NOT_FOUND = ResponseFormat.builder()
            .code(14014)
            .message("reservation not found")
            .build();

    public static final ResponseFormat COMMENT_NOT_FOUND = ResponseFormat.builder()
            .code(14015)
            .message("comment not found")
            .build();

    public static final ResponseFormat PERSIST_FEATURE = ResponseFormat.builder()
            .code(16053)
            .message("feature cannot be persisted")
            .build();

    public static final ResponseFormat PERSIST_STORAGE = ResponseFormat.builder()
            .code(16054)
            .message("storage cannot be persisted")
            .build();

    public static final ResponseFormat PERSIST_ADDRESS = ResponseFormat.builder()
            .code(16059)
            .message("address cannot be persisted")
            .build();

    public static final ResponseFormat PERSIST_BOOKMARK = ResponseFormat.builder()
            .code(16060)
            .message("bookmark cannot be persisted")
            .build();

    public static final ResponseFormat PERSIST_COMMENT = ResponseFormat.builder()
            .code(16061)
            .message("comment cannot be persisted")
            .build();

    public static final ResponseFormat PERSIST_FAVOURITES_BOOK = ResponseFormat.builder()
            .code(16062)
            .message("favourite cannot be persisted")
            .build();

    public static final ResponseFormat ILLEGAL_REQUEST = ResponseFormat.builder()
            .code(-1)
            .message("illegal request is detected")
            .build();

    public static final ResponseFormat EXPIRED_TOKEN = ResponseFormat.builder()
            .code(10020)
            .message("token is expired")
            .build();

    public static final ResponseFormat AUTHENTICATION_CREDENTIAL_NOT_FOUND = ResponseFormat.builder()
            .code(10050)
            .message("authentication credential not found")
            .build();

    public static final ResponseFormat NOT_REMOVABLE_STORAGE = ResponseFormat.builder()
            .code(10051)
            .message("storage is not removable")
            .build();

    public static final ResponseFormat FAILED_UPLOAD_FILE = ResponseFormat.builder()
            .code(10052)
            .message("uploading file was failed")
            .build();

    public static final ResponseFormat EMPTY_FILE_UPLOADED = ResponseFormat.builder()
            .code(10053)
            .message("empty file doesn't upload")
            .build();

}
