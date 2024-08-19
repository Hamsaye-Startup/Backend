package com.hamsaye.chat.applications.mapper;


import com.hamsaye.chat.applications.exceptions.CustomRuntimeException;
import com.hamsaye.chat.applications.messages.ExceptionMessage;
import com.hamsaye.chat.applications.messages.ResponseMessage;
import com.hamsaye.chat.applications.responses.ResponseMessageType;
import jakarta.ws.rs.InternalServerErrorException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ResponseMessageMapper {

    public ResponseMessage toResponse(Object object) {
        return ResponseMessage.builder()
                .code(ResponseMessageType.SUCCESS.code())
                .message(ResponseMessageType.SUCCESS.message())
                .timestamp(LocalDateTime.now())
                .result(object)
                .build();
    }

    public ResponseMessage toResponse() {
        return ResponseMessage.builder()
                .code(ResponseMessageType.SUCCESS.code())
                .message(ResponseMessageType.SUCCESS.message())
                .timestamp(LocalDateTime.now())
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
