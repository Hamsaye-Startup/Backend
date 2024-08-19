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

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageServiceManagement messageServiceManagement;
    private final ResponseMessageMapper mapper;

    @MessageMapping("/message")
    public void sendMessage(@Payload MessageRequest messageRequest) {
        messageServiceManagement.sendMessage(messageRequest);
    }

    @GetMapping("{chatId}/messages")
    public @ResponseBody ResponseEntity<?> loadMessagesByConversationId(
            @PathVariable("chatId") UUID uid,
            Pageable pageable
    ) {
        Page<MessageEntity> responses = messageServiceManagement.findMessagesByConversationId(uid, pageable);
        return ResponseEntity.ok(mapper.toResponse(responses));
    }
}
