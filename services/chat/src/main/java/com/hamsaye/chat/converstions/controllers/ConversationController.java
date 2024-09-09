package com.hamsaye.chat.converstions.controllers;

import com.hamsaye.chat.applications.mapper.ResponseMessageMapper;
import com.hamsaye.chat.converstions.models.ConversationEntity;
import com.hamsaye.chat.converstions.requests.NewConversationRequest;
import com.hamsaye.chat.converstions.services.ConversationServiceManagement;
import com.hamsaye.chat.messages.requests.MessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.UUID;

/**
 * Controller for handling conversation-related requests and messaging.
 * Provides endpoints for creating, retrieving, and deleting conversations.
 *
 * <p>This controller integrates with the {@link ConversationServiceManagement} to manage conversation data
 * and the {@link ResponseMessageMapper} to map responses.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Controller
@RequiredArgsConstructor
public class ConversationController {

    /**
     * @see com.hamsaye.chat.converstions.services.ConversationServiceManagement
     */
    private final ConversationServiceManagement conversationServiceManagement;

    /**
     * @see com.hamsaye.chat.applications.mapper.ResponseMessageMapper
     */
    private final ResponseMessageMapper mapper;

    /**
     * Handles the creation of a new conversation.
     * Receives a {@link NewConversationRequest} and processes it to create a conversation.
     *
     * @param conversationRequest The request containing details for the new conversation.
     * @since 1.0
     */
    @MessageMapping("/conversation.addConversation")
    public void createConversation(@Payload NewConversationRequest conversationRequest) {
        conversationServiceManagement.createConversation(
                MessageRequest.builder()
                        .senderId(conversationRequest.senderId())
                        .content(conversationRequest.content())
                        .build(),
                conversationRequest.recipientId()
        );
    }

    /**
     * Retrieves all conversations for a specified user.
     *
     * @param uid The unique identifier of the user whose conversations are to be retrieved.
     * @return A {@link ResponseEntity} containing the list of conversations for the user.
     * @since 1.0
     */
    @GetMapping("{userId}/conversations")
    public @ResponseBody ResponseEntity<?> loadAllConversationsByUser(
            @PathVariable("userId") UUID uid
    ) {
        List<ConversationEntity> responses = conversationServiceManagement.findAllConversationByUserUid(uid);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

    /**
     * Deletes a conversation by its unique identifier.
     *
     * @param uid The unique identifier of the conversation to be deleted.
     * @return A {@link ResponseEntity} containing the deleted conversation details.
     * @since 1.0
     */
    @DeleteMapping("/conversation/{conversationId}")
    public @ResponseBody ResponseEntity<?> deleteConversationById(
            @PathVariable("conversationId") UUID uid
    ) {
        ConversationEntity response = conversationServiceManagement.deleteConversation(uid);
        return ResponseEntity.ok(mapper.toResponse(response));
    }
}
