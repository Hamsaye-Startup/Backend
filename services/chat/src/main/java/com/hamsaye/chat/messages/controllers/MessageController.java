package com.hamsaye.chat.messages.controllers;

import com.hamsaye.chat.applications.mapper.ResponseMessageMapper;
import com.hamsaye.chat.messages.models.MessageEntity;
import com.hamsaye.chat.messages.requests.MessageRequest;
import com.hamsaye.chat.messages.services.MessageServiceManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * Controller class responsible for handling message-related endpoints.
 * <p>
 * This controller provides endpoints for sending messages and loading messages by conversation ID.
 * It uses {@link MessageServiceManagement} for business logic and {@link ResponseMessageMapper} for response mapping.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Controller
@RequiredArgsConstructor
public class MessageController {

    /**
     * @see com.hamsaye.chat.messages.services.MessageServiceManagement
     */
    private final MessageServiceManagement messageServiceManagement;

    /**
     * @see com.hamsaye.chat.applications.mapper.ResponseMessageMapper
     */
    private final ResponseMessageMapper mapper;

    /**
     * Handles sending a message.
     * <p>
     * This method processes incoming messages through WebSocket and passes them to the
     * {@link MessageServiceManagement} for further processing.
     * </p>
     *
     * @param messageRequest the message request containing details about the message to be sent
     * @since 1.0
     */
    @MessageMapping("/message")
    public void sendMessage(@Payload MessageRequest messageRequest) {
        messageServiceManagement.sendMessage(messageRequest);
    }

    /**
     * Loads messages by conversation ID.
     * <p>
     * This method retrieves messages associated with a specific conversation ID, using pagination
     * to manage the amount of data returned. The results are then mapped to a response format using
     * {@link ResponseMessageMapper}.
     * </p>
     *
     * @param uid the unique identifier of the conversation
     * @param pageable pagination information
     * @return a {@link ResponseEntity} containing a page of {@link MessageEntity} objects
     * @since 1.0
     */
    @GetMapping("{chatId}/messages")
    public @ResponseBody ResponseEntity<?> loadMessagesByConversationId(
            @PathVariable("chatId") UUID uid,
            Pageable pageable
    ) {
        Page<MessageEntity> responses = messageServiceManagement.findMessagesByConversationId(uid, pageable);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }
}
