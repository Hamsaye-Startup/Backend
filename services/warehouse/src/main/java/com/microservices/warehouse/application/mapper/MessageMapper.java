package com.microservices.warehouse.application.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microservices.warehouse.application.api.config.ApiResponseConfig;
import com.microservices.warehouse.application.api.response.ResponseFormat;
import com.microservices.warehouse.application.api.response.ExceptionMessage;
import com.microservices.warehouse.application.api.response.ResponseMessage;
import com.microservices.warehouse.application.exceptions.CustomRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Service class for mapping exceptions and response objects to standard response formats.
 *
 * <p>This service provides methods to create standardized {@link ResponseMessage} and {@link ExceptionMessage}
 * objects for success responses and error handling, respectively.</p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class MessageMapper {

    private final ApiResponseConfig apiResponseConfig;

    /**
     * Creates a {@link ResponseMessage} for a successful response with a result object.
     *
     * @param result the result object to include in the response.
     * @param requestId the user id to include in the response.
     * @param path the request path to include in the response.
     * @return a {@link ResponseMessage} with a success code and the provided result object.
     */
    public ResponseMessage toResponse(
            Object result,
            UUID requestId,
            String path
    ) {
        // Load the success configuration
        ResponseFormat response = apiResponseConfig.getResponseByKey("success");
        return ResponseMessage.builder()
                .code(response.code())
                .status(response.status())
                .title(response.title())
                .message("The operation has successfully done.")
                .requestId(requestId != null ? requestId.toString() : null)
                .timestamp(LocalDateTime.now())
                .path(path)
                .result(result)
                .build();
    }

    /**
     * Creates a {@link ResponseMessage} for a successful response without a result object.
     *
     * @param requestId the user id to include in the response.
     * @param path the request path to include in the response.
     * @return a {@link ResponseMessage} with a success code.
     */
    public ResponseMessage toResponse(
            UUID requestId,
            String path
    ) {
        // Load the success configuration
        ResponseFormat response = apiResponseConfig.getResponseByKey("success");
        return ResponseMessage.builder()
                .code(response.code())
                .status(response.status())
                .title(response.title())
                .message("The operation has successfully done.")
                .requestId(requestId != null ? requestId.toString() : null)
                .timestamp(LocalDateTime.now())
                .path(path)
                .build();
    }

    public <T extends CustomRuntimeException> ExceptionMessage toException(
            T ex,
            String requestId,
            String path
    ) {
        ResponseFormat exception = apiResponseConfig.getResponseByKey(ex.getKey());
        return ExceptionMessage.builder()
                .code(exception.code())
                .status(exception.status())
                .title(exception.title())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(path)
                .requestId(requestId)
                .build();
    }

    public ExceptionMessage toException(
            Map<String, List<String>> errors,
            String requestId,
            String path
    ) throws JsonProcessingException {
        ResponseFormat validation = apiResponseConfig.getResponseByKey("validation");
        return ExceptionMessage.builder()
                .code(validation.code())
                .status(validation.status())
                .title(validation.title())
                .message(errors)
                .timestamp(LocalDateTime.now())
                .path(path)
                .requestId(requestId)
                .build();
    }
}