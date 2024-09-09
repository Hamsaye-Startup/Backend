package com.microservices.reservation.applications.mapper;

import com.microservices.reservation.applications.exceptions.CustomRuntimeException;
import com.microservices.reservation.applications.messages.ExceptionMessage;
import com.microservices.reservation.applications.messages.ResponseMessage;
import com.microservices.reservation.applications.responses.ResponseMessageType;
import jakarta.ws.rs.InternalServerErrorException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
public class MessageMapper {

    /**
     * Creates a {@link ResponseMessage} for a successful response with a result object.
     *
     * @param object the result object to include in the response.
     * @return a {@link ResponseMessage} with a success code and the provided result object.
     */
    public ResponseMessage toResponse(Object object) {
        return ResponseMessage.builder()
                .code(ResponseMessageType.SUCCESS.code())
                .message(ResponseMessageType.SUCCESS.message())
                .timestamp(LocalDateTime.now())
                .result(object)
                .build();
    }

    /**
     * Creates a {@link ResponseMessage} for a successful response without a result object.
     *
     * @return a {@link ResponseMessage} with a success code.
     */
    public ResponseMessage toResponse() {
        return ResponseMessage.builder()
                .code(ResponseMessageType.SUCCESS.code())
                .message(ResponseMessageType.SUCCESS.message())
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * Creates an {@link ExceptionMessage} for a given {@link CustomRuntimeException} with a specific code.
     *
     * @param ex the {@link CustomRuntimeException} to map.
     * @param code the error code to include in the exception message.
     * @param <T> the type of the exception.
     * @return an {@link ExceptionMessage} with the provided code and exception details.
     */
    public <T extends CustomRuntimeException> ExceptionMessage toException(T ex, int code) {
        ExceptionMessage.ExceptionMessageBuilder builder = ExceptionMessage.builder()
                .code(code)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now());

        if (ex.getCause() != null) {
            builder.cause(ex.getCause().getMessage());
        }

        return builder.build();
    }

    /**
     * Creates an {@link ExceptionMessage} for an {@link InternalServerErrorException} with a specific code.
     *
     * @param ex the {@link InternalServerErrorException} to map.
     * @param code the error code to include in the exception message.
     * @return an {@link ExceptionMessage} with the provided code and exception details.
     */
    public ExceptionMessage toException(InternalServerErrorException ex, int code) {
        ExceptionMessage.ExceptionMessageBuilder builder = ExceptionMessage.builder()
                .code(code)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now());

        if (ex.getCause() != null) {
            builder.cause(ex.getCause().getMessage());
        }

        return builder.build();
    }
}
