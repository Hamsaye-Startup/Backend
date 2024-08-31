package com.hamsaye.chat.applications.handler;

import com.hamsaye.chat.applications.exceptions.ExpiredTokenException;
import com.hamsaye.chat.applications.mapper.ResponseMessageMapper;
import com.hamsaye.chat.applications.messages.ExceptionMessage;
import com.hamsaye.chat.applications.responses.ResponseMessageType;
import com.hamsaye.chat.applications.utils.log.CustomLogger;
import com.hamsaye.chat.converstions.exceptions.NotFoundConversationException;
import com.hamsaye.chat.messages.exceptions.ConversationAlreadyExistsException;
import com.hamsaye.chat.messages.exceptions.NotFoundMessageException;
import com.hamsaye.chat.users.exceptions.NotFoundUserException;
import com.hamsaye.chat.users.exceptions.PersistUserException;
import com.hamsaye.chat.websocket.exceptions.AuthenticationCredentialNotFoundException;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerImpl {

    private final ResponseMessageMapper mapper;
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

    @ExceptionHandler({NotFoundUserException.class})
    public ResponseEntity<?> getNotFoundUserException(NotFoundUserException ex, WebRequest request) {
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

    @ExceptionHandler({NotFoundMessageException.class})
    public ResponseEntity<?> getNotFoundMessageException(NotFoundMessageException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage(), ex.getCause());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.CHAT_MESSAGE_NOT_FOUND.code());
        return new ResponseEntity<>(exception, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({NotFoundConversationException.class})
    public ResponseEntity<?> getNotFoundConversationException(NotFoundConversationException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage(), ex.getCause());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.CHAT_CONVERSATION_NOT_FOUND.code());
        return new ResponseEntity<>(exception, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({ConversationAlreadyExistsException.class})
    public ResponseEntity<?> getConversationAlreadyExistsException(ConversationAlreadyExistsException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage(), ex.getCause());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.CHAT_CONVERSATION_ALREADY_EXISTS.code());
        return new ResponseEntity<>(exception, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({AuthenticationCredentialNotFoundException.class})
    public ResponseEntity<?> getAuthenticationCredentialNotFoundException(AuthenticationCredentialNotFoundException ex, WebRequest request) {
        // generate a log
        logger.warn(ex.getMessage(), ex.getCause());

        // generate exception message
        ExceptionMessage exception = mapper.toException(ex, ResponseMessageType.AUTHENTICATION_CREDENTIAL_NOT_FOUND.code());
        return new ResponseEntity<>(exception, HttpStatus.UNAUTHORIZED);
    }
}
