package com.hamsaye.chat.messages.services;

import com.hamsaye.chat.kafka.producers.MessageProducerService;
import com.hamsaye.chat.messages.models.MessageEntity;
import com.hamsaye.chat.messages.models.MessageNotification;
import com.hamsaye.chat.messages.models.MessageNotifyType;
import com.hamsaye.chat.messages.requests.MessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceManagement {

    private final MessageService messageService;

    private final MessageProducerService messageProducerService;

    public void sendMessage(MessageRequest messageRequest) {

        // send a notification
        messageProducerService.send(MessageNotification.builder()
                .senderId(messageRequest.senderId())
                .conversationId(messageRequest.conversationId())
                .content(messageRequest.content())
                .message(MessageNotifyType.SEND_NEW_MESSAGE.getMessage())
                .type(MessageNotifyType.SEND_NEW_MESSAGE)
                .build());
    }

    public Page<MessageEntity> findMessagesByConversationId(
            UUID conversationId,
            Pageable pageable
    ) {
        return messageService.loadMessagesByConversationId(conversationId, pageable);
    }
}
