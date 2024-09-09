package com.microservices.warehouse.applications.responses;

import org.springframework.stereotype.Component;

/**
 * A component class that defines various {@link ResponseFormat} constants for standard response messages.
 *
 * <p>This class contains predefined response formats for different scenarios including success, errors,
 * and various specific cases such as scope not found or feature persistence failure.</p>
 *
 * <p>The constants provided in this class ensure consistency in response messages and codes across the warehouse service application.</p>
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
     * Response format for a feature not found error.
     */
    public static final ResponseFormat FEATURE_NOT_FOUND = ResponseFormat.builder()
            .code(14005)
            .message("feature not found")
            .build();

    /**
     * Response format for a policy not found error.
     */
    public static final ResponseFormat POLICY_NOT_FOUND = ResponseFormat.builder()
            .code(14006)
            .message("policy not found")
            .build();

    /**
     * Response format for a storage not found error.
     */
    public static final ResponseFormat STORAGE_NOT_FOUND = ResponseFormat.builder()
            .code(14007)
            .message("storage not found")
            .build();

    /**
     * Response format for a bookmark not found error.
     */
    public static final ResponseFormat BOOKMARK_NOT_FOUND = ResponseFormat.builder()
            .code(14008)
            .message("bookmark not found")
            .build();

    /**
     * Response format for a reservation not found error.
     */
    public static final ResponseFormat RESERVATION_NOT_FOUND = ResponseFormat.builder()
            .code(14014)
            .message("reservation not found")
            .build();

    /**
     * Response format for a comment not found error.
     */
    public static final ResponseFormat COMMENT_NOT_FOUND = ResponseFormat.builder()
            .code(14015)
            .message("comment not found")
            .build();

    /**
     * Response format for a failure to persist feature information.
     */
    public static final ResponseFormat PERSIST_FEATURE = ResponseFormat.builder()
            .code(16053)
            .message("feature cannot be persisted")
            .build();

    /**
     * Response format for a failure to persist storage information.
     */
    public static final ResponseFormat PERSIST_STORAGE = ResponseFormat.builder()
            .code(16054)
            .message("storage cannot be persisted")
            .build();

    /**
     * Response format for a failure to persist address information.
     */
    public static final ResponseFormat PERSIST_ADDRESS = ResponseFormat.builder()
            .code(16059)
            .message("address cannot be persisted")
            .build();

    /**
     * Response format for a failure to persist bookmark information.
     */
    public static final ResponseFormat PERSIST_BOOKMARK = ResponseFormat.builder()
            .code(16060)
            .message("bookmark cannot be persisted")
            .build();

    /**
     * Response format for a failure to persist comment information.
     */
    public static final ResponseFormat PERSIST_COMMENT = ResponseFormat.builder()
            .code(16061)
            .message("comment cannot be persisted")
            .build();

    /**
     * Response format for a failure to persist favourite book information.
     */
    public static final ResponseFormat PERSIST_FAVOURITES_BOOK = ResponseFormat.builder()
            .code(16062)
            .message("favourite cannot be persisted")
            .build();

    /**
     * Response format for an illegal request.
     */
    public static final ResponseFormat ILLEGAL_REQUEST = ResponseFormat.builder()
            .code(-1)
            .message("illegal request is detected")
            .build();

    /**
     * Response format for missing authentication credentials.
     */
    public static final ResponseFormat AUTHENTICATION_CREDENTIAL_NOT_FOUND = ResponseFormat.builder()
            .code(10050)
            .message("authentication credential not found")
            .build();

    /**
     * Response format for storage that is not removable.
     */
    public static final ResponseFormat NOT_REMOVABLE_STORAGE = ResponseFormat.builder()
            .code(10051)
            .message("storage is not removable")
            .build();

    /**
     * Response format for a failed file upload.
     */
    public static final ResponseFormat FAILED_UPLOAD_FILE = ResponseFormat.builder()
            .code(10052)
            .message("uploading file was failed")
            .build();

    /**
     * Response format for an empty file upload attempt.
     */
    public static final ResponseFormat EMPTY_FILE_UPLOADED = ResponseFormat.builder()
            .code(10053)
            .message("empty file doesn't upload")
            .build();
}
