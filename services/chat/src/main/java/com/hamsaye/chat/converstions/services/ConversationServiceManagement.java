package com.hamsaye.chat.converstions.services;

import com.hamsaye.chat.converstions.models.ConversationEntity;
import com.hamsaye.chat.kafka.producers.MessageProducerService;
import com.hamsaye.chat.messages.mappers.MessageMapper;
import com.hamsaye.chat.messages.models.MessageEntity;
import com.hamsaye.chat.messages.models.MessageNotification;
import com.hamsaye.chat.messages.models.MessageNotifyType;
import com.hamsaye.chat.messages.requests.MessageRequest;
import com.hamsaye.chat.messages.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConversationServiceManagement {

    private final ConversationService conversationService;

    private final MessageService messageService;
    private final MessageMapper messageMapper;

    private final MessageProducerService messageProducerService;
    public void createConversation(MessageRequest messageRequest, UUID recipientId) {

        // convert the message request to message entity
        MessageEntity message = messageMapper.toMessage(messageRequest);

        // persist the conversation and message
        MessageEntity sentMessage = messageService.sendMessage(message, recipientId, true);

        // send a notification
        messageProducerService.send(MessageNotification.builder()
                .id(sentMessage.getId())
                .conversationId(sentMessage.getConversationId())
                .senderId(sentMessage.getSenderId())
                .content(sentMessage.getContent())
                .message(MessageNotifyType.UPDATE_CHAT_MESSAGE.getMessage())
                .type(MessageNotifyType.UPDATE_CHAT_MESSAGE)
                .build());
    }

    public ConversationEntity deleteConversation(UUID conversationId) {

        // find the conversation by id
        ConversationEntity conversation = conversationService.findConversationByUid(conversationId);

        // delete the conversation and messages
        ConversationEntity deleted = conversationService.deleteConversation(conversation);
        messageService.deleteMessagesByConversationId(conversation.getUid());
        return deleted;
    }

    // load all conversations belong to a user
    public List<ConversationEntity> findAllConversationByUserUid(UUID userId) {
        return conversationService.findAllConversationsByUserUid(userId);
    }
}
