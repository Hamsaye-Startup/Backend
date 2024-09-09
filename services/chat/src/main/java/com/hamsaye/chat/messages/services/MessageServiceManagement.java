package com.hamsaye.chat.messages.services;

import com.hamsaye.chat.kafka.producers.MessageProducerService;
import com.hamsaye.chat.messages.models.MessageEntity;
import com.hamsaye.chat.kafka.requests.MessageNotifyRequest;
import com.hamsaye.chat.kafka.requests.MessageNotifyType;
import com.hamsaye.chat.messages.requests.MessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service class responsible for managing messages.
 * <p>
 * This service handles sending messages and retrieving messages by conversation ID. It integrates with
 * Kafka producers to send notifications about new messages.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class MessageServiceManagement {

    /**
     * Service for message-related operations.
     * @see com.hamsaye.chat.messages.services.MessageService
     */
    private final MessageService messageService;

    /**
     * Service for producing Kafka messages.
     * @see com.hamsaye.chat.kafka.producers.MessageProducerService
     */
    private final MessageProducerService messageProducerService;

    /**
     * Sends a message and produces a notification about it.
     * <p>
     * This method constructs a {@link MessageNotifyRequest} from the provided {@link MessageRequest},
     * then uses the {@link MessageProducerService} to send a notification about the new message.
     * </p>
     *
     * @param messageRequest the request containing details about the message to be sent
     * @since 1.0
     */
    public void sendMessage(MessageRequest messageRequest) {

        // send a notification
        messageProducerService.send(MessageNotifyRequest.builder()
                .senderId(messageRequest.senderId())
                .conversationId(messageRequest.conversationId())
                .content(messageRequest.content())
                .message(MessageNotifyType.SEND_NEW_MESSAGE.getMessage())
                .type(MessageNotifyType.SEND_NEW_MESSAGE)
                .build());
    }

    /**
     * Retrieves messages by conversation ID.
     * <p>
     * This method queries the {@link MessageService} to load messages associated with the specified
     * conversation ID, using pagination to manage the results.
     * </p>
     *
     * @param conversationId the unique identifier of the conversation
     * @param pageable pagination information
     * @return a {@link Page} of {@link MessageEntity} objects
     * @since 1.0
     */
    public Page<MessageEntity> findMessagesByConversationId(
            UUID conversationId,
            Pageable pageable
    ) {
        return messageService.loadMessagesByConversationId(conversationId, pageable);
    }
}
