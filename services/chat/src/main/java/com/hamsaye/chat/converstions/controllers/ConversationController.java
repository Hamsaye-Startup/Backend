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

@Controller
@RequiredArgsConstructor
public class ConversationController {

    private final ConversationServiceManagement conversationServiceManagement;
    private final ResponseMessageMapper mapper;

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

    @GetMapping("{userId}/conversations")
    public @ResponseBody ResponseEntity<?> loadAllConversationsByUser(
            @PathVariable("userId") UUID uid
    ) {
        List<ConversationEntity> responses = conversationServiceManagement.findAllConversationByUserUid(uid);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }

    @DeleteMapping("/conversation/{conversationId}")
    public @ResponseBody ResponseEntity<?> deleteConversationById(
            @PathVariable("conversationId") UUID uid
    ) {
        ConversationEntity response = conversationServiceManagement.deleteConversation(uid);
        return ResponseEntity.ok(mapper.toResponse(response));
    }
}
