package com.hamsaye.customer.application.mapper;

import com.hamsaye.customer.application.exceptions.CustomRuntimeException;
import com.hamsaye.customer.application.messages.ExceptionMessage;
import com.hamsaye.customer.application.messages.ResponseMessage;
import com.hamsaye.customer.application.responses.ResponseMessageType;
import jakarta.ws.rs.InternalServerErrorException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageMapper {

    public ResponseMessage toResponse(Object object) {
        return ResponseMessage.builder()
                .code(ResponseMessageType.SUCCESS.code())
                .message(ResponseMessageType.SUCCESS.message())
                .timestamp(LocalDateTime.now())
                .result(object)
                .build();
    }

    public <T extends CustomRuntimeException> ExceptionMessage toException(T ex, int code) {
        ExceptionMessage exceptionMessage;
        if (ex.getCause() != null) {
            exceptionMessage = ExceptionMessage.builder()
                    .code(code)
                    .message(ex.getMessage())
                    .timestamp(LocalDateTime.now())
                    .cause(ex.getCause().getMessage())
                    .build();
        } else {
            exceptionMessage = ExceptionMessage.builder()
                    .code(code)
                    .message(ex.getMessage())
                    .timestamp(LocalDateTime.now())
                    .build();
        }
        return exceptionMessage;
    }

    public ExceptionMessage toException(InternalServerErrorException ex, int code) {
        ExceptionMessage exceptionMessage;
        if (ex.getCause() != null) {
            exceptionMessage = ExceptionMessage.builder()
                    .code(code)
                    .message(ex.getMessage())
                    .timestamp(LocalDateTime.now())
                    .cause(ex.getCause().getMessage())
                    .build();
        } else {
            exceptionMessage = ExceptionMessage.builder()
                    .code(code)
                    .message(ex.getMessage())
                    .timestamp(LocalDateTime.now())
                    .build();
        }
        return exceptionMessage;
    }
}
