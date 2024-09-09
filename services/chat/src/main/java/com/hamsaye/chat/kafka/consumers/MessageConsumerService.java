package com.hamsaye.chat.kafka.consumers;

import com.hamsaye.chat.applications.mapper.ResponseMessageMapper;
import com.hamsaye.chat.messages.mappers.MessageMapper;
import com.hamsaye.chat.messages.models.MessageEntity;
import com.hamsaye.chat.kafka.requests.MessageNotifyRequest;
import com.hamsaye.chat.kafka.requests.MessageNotifyType;
import com.hamsaye.chat.messages.services.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for consuming Kafka messages related to chat messages.
 * <p>
 * This service listens to Kafka topics for chat message notifications and updates the chat messages
 * in the system based on the type of notification received. It supports sending new messages and
 * updating existing messages.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageConsumerService {

    /**
     * Service for managing chat messages.
     * @see com.hamsaye.chat.messages.services.MessageService
     */
    private final MessageService messageService;

    /**
     * Mapper for converting Kafka messages to {@link MessageEntity}.
     * @see com.hamsaye.chat.messages.mappers.MessageMapper
     */
    private final MessageMapper messageMapper;

    /**
     * Template for sending WebSocket messages.
     * @see org.springframework.messaging.simp.SimpMessagingTemplate
     */
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Mapper for response message transformations.
     * @see com.hamsaye.chat.applications.mapper.ResponseMessageMapper
     */
    private final ResponseMessageMapper mapper;

    /**
     * Listens to Kafka messages related to chat messages.
     * <p>
     * This method processes notifications for new chat messages and updates to existing chat messages.
     * It updates the message information in the system and sends updates via WebSocket.
     * </p>
     *
     * @param notification the {@link MessageNotifyRequest} containing chat message notification details
     * @param key the Kafka message key (optional)
     * @since 1.0
     */
    @KafkaListener(
            id = "chat-message-listener-id",
            topics = "topic-chat-messages",
            groupId = "group-id",
            containerFactory = "concurrentKafkaListenerContainerFactory"
    )
    public void messageListener(
            MessageNotifyRequest notification,
            @Header(name = KafkaHeaders.RECEIVED_KEY, required = false) String key
    ) {

        log.info("key[{}] message is received by {}: {}",
                key == null ? "none" : key,
                "chat-message-listener-id",
                notification
        );

        if (notification.type().equals(MessageNotifyType.SEND_NEW_MESSAGE)) {
            sendNewMessage(notification);
        }
        else if (notification.type().equals(MessageNotifyType.UPDATE_CHAT_MESSAGE)) {
            sendAvailableMessage(notification);
        }
    }

    /**
     * Sends a new chat message based on the notification received.
     * <p>
     * This method creates a new {@link MessageEntity} from the notification and saves it using
     * the {@link MessageService}. It then sends the saved message via WebSocket to the appropriate
     * conversation.
     * </p>
     *
     * @param notification the {@link MessageNotifyRequest} containing details of the new chat message
     * @since 1.0
     */
    private void sendNewMessage(MessageNotifyRequest notification) {

        // send new message
        MessageEntity message = messageMapper.toMessage(notification);

        // send a message to existing conversation
        MessageEntity sentMessage = messageService.sendMessage(
                message,
                null,
                false
        );

        // return by websocket
        messagingTemplate.convertAndSend(
                "/conversation" + sentMessage.getConversationId() + "/queue/messages",
                ResponseEntity.ok(mapper.toResponse(sentMessage))
        );
    }

    /**
     * Sends an available chat message based on the notification received.
     * <p>
     * This method retrieves the message using its ID from the {@link MessageService} and then
     * sends the message details via WebSocket to the appropriate conversation.
     * </p>
     *
     * @param notification the {@link MessageNotifyRequest} containing the ID and conversation details of the chat message
     * @since 1.0
     */
    private void sendAvailableMessage(MessageNotifyRequest notification) {

        // find the message
        MessageEntity message = messageService.findMessageById(notification.id());

        // return by websocket
        messagingTemplate.convertAndSend(
                "/conversation" + notification.conversationId() + "/queue/messages",
                ResponseEntity.ok(mapper.toResponse(message))
        );
    }
}
