package com.microservices.user.application.mapper;

import com.microservices.user.application.exceptions.CustomRuntimeException;
import com.microservices.user.application.messages.ExceptionMessage;
import com.microservices.user.application.messages.ResponseMessage;
import com.microservices.user.application.responses.ResponseMessageType;
import jakarta.ws.rs.InternalServerErrorException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public ExceptionMessage toException(UsernameNotFoundException ex, int code) {
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
