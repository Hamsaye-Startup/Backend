package com.hamsaye.report.applications.mapper;

import com.hamsaye.report.applications.exceptions.CustomRuntimeException;
import com.hamsaye.report.applications.messages.ExceptionMessage;
import com.hamsaye.report.applications.messages.ResponseMessage;
import com.hamsaye.report.applications.responses.ResponseMessageType;
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

    public ResponseMessage toResponse() {
        return ResponseMessage.builder()
                .code(ResponseMessageType.SUCCESS.code())
                .message(ResponseMessageType.SUCCESS.message())
                .timestamp(LocalDateTime.now())
                .build();
    }

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
