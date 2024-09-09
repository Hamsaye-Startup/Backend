package com.hamsaye.chat.converstions.services;

import com.hamsaye.chat.converstions.models.ConversationEntity;
import com.hamsaye.chat.kafka.producers.MessageProducerService;
import com.hamsaye.chat.messages.mappers.MessageMapper;
import com.hamsaye.chat.messages.models.MessageEntity;
import com.hamsaye.chat.kafka.requests.MessageNotifyRequest;
import com.hamsaye.chat.kafka.requests.MessageNotifyType;
import com.hamsaye.chat.messages.requests.MessageRequest;
import com.hamsaye.chat.messages.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service for managing conversation-related operations.
 * This service handles the creation, deletion, and retrieval of conversations,
 * as well as sending notifications about conversation updates.
 *
 * <p>It integrates with other services and mappers to perform its tasks:
 * <ul>
 *     <li>{@link ConversationService} for managing conversation data.</li>
 *     <li>{@link MessageService} for handling message operations.</li>
 *     <li>{@link MessageMapper} for mapping message requests to entities.</li>
 *     <li>{@link MessageProducerService} for sending Kafka notifications.</li>
 * </ul>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ConversationServiceManagement {

    /**
     * Service for managing conversation data.
     */
    private final ConversationService conversationService;

    /**
     * Service for handling message operations.
     */
    private final MessageService messageService;

    /**
     * Mapper for converting message requests to message entities.
     */
    private final MessageMapper messageMapper;

    /**
     * Service for sending Kafka notifications about messages.
     */
    private final MessageProducerService messageProducerService;

    /**
     * Creates a new conversation by sending a message and notifying relevant parties.
     *
     * @param messageRequest The request containing details of the message to be sent.
     * @param recipientId The unique identifier of the recipient of the message.
     */
    public void createConversation(MessageRequest messageRequest, UUID recipientId) {

        // Convert the message request to message entity
        MessageEntity message = messageMapper.toMessage(messageRequest);

        // Persist the conversation and message
        MessageEntity sentMessage = messageService.sendMessage(message, recipientId, true);

        // Send a notification
        messageProducerService.send(MessageNotifyRequest.builder()
                .id(sentMessage.getId())
                .conversationId(sentMessage.getConversationId())
                .senderId(sentMessage.getSenderId())
                .content(sentMessage.getContent())
                .message(MessageNotifyType.UPDATE_CHAT_MESSAGE.getMessage())
                .type(MessageNotifyType.UPDATE_CHAT_MESSAGE)
                .build());
    }

    /**
     * Deletes a conversation by its unique identifier.
     *
     * @param conversationId The unique identifier of the conversation to be deleted.
     * @return The deleted {@link ConversationEntity}.
     */
    public ConversationEntity deleteConversation(UUID conversationId) {

        // Find the conversation by id
        ConversationEntity conversation = conversationService.findConversationByUid(conversationId);

        // Delete the conversation and messages
        ConversationEntity deleted = conversationService.deleteConversation(conversation);
        messageService.deleteMessagesByConversationId(conversation.getUid());
        return deleted;
    }

    /**
     * Retrieves all conversations belonging to a specified user.
     *
     * @param userId The unique identifier of the user whose conversations are to be retrieved.
     * @return A list of {@link ConversationEntity} objects associated with the user.
     */
    public List<ConversationEntity> findAllConversationByUserUid(UUID userId) {
        return conversationService.findAllConversationsByUserUid(userId);
    }
}
