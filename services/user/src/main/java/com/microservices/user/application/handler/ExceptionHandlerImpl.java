package com.microservices.user.application.handler;

import com.microservices.user.application.exceptions.ExpiredTokenException;
import com.microservices.user.application.exceptions.NotFoundScopeException;
import com.microservices.user.application.mapper.MessageMapper;
import com.microservices.user.application.messages.ExceptionMessage;
import com.microservices.user.application.responses.ResponseMessageType;
import com.microservices.user.roles.exceptions.NotFoundRoleException;
import com.microservices.user.roles.exceptions.PersistRoleException;
import com.microservices.user.users.exceptions.IllegalRequestException;
import com.microservices.user.users.exceptions.NotFoundUserException;
import com.microservices.user.users.exceptions.PersistUserException;
import com.microservices.user.application.utils.log.CustomLogger;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerImpl {

    private final MessageMapper mapper;
    private final CustomLogger logger = CustomLogger.getInstance();

    @ExceptionHandler({InternalServerErrorException.class})
    public ResponseEntity<?> getInternalServerErrorException(InternalServerErrorException ex, WebRequest request) {
        // generate a log
        logger.error(ex.getMessage(), ex.getCause());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.INTERNAL.code());
        return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ExpiredTokenException.class})
    public ResponseEntity<?> getExpiredTokenException(ExpiredTokenException ex, WebRequest request) {
        // generate a log
        logger.info(ex.getMessage());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.EXPIRED_TOKEN.code());
        return new ResponseEntity<>(exception, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({NotFoundScopeException.class})
    public ResponseEntity<?> getNotFoundScopeException(NotFoundScopeException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.SCOPE_NOT_FOUND.code());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotFoundUserException.class})
    public ResponseEntity<?> getNotFoundUserException(NotFoundUserException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage(), ex.getCause());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.USER_NOT_FOUND.code());
        return new ResponseEntity<>(exception, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({NotFoundRoleException.class})
    public ResponseEntity<?> getNotFoundRoleException(NotFoundRoleException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage(), ex.getCause());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.ROLE_NOT_FOUND.code());
        return new ResponseEntity<>(exception, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<?> getNotFoundUserException(UsernameNotFoundException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage(), ex.getCause());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.USER_NOT_FOUND.code());
        return new ResponseEntity<>(exception, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({PersistUserException.class})
    public ResponseEntity<?> getPersistUserException(PersistUserException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage(), ex.getCause());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.PERSIST_USER.code());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({PersistRoleException.class})
    public ResponseEntity<?> getPersistRoleException(PersistRoleException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage(), ex.getCause());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.PERSIST_ROLE.code());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IllegalRequestException.class})
    public ResponseEntity<?> getIllegalRequestException(IllegalRequestException ex, WebRequest request) {
        // generate a log
        logger.error(ex.getMessage(), ex.getCause());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.ILLEGAL_REQUEST.code());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
}
