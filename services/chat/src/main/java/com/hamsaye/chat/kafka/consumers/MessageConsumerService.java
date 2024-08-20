package com.hamsaye.chat.kafka.consumers;

import com.hamsaye.chat.applications.mapper.ResponseMessageMapper;
import com.hamsaye.chat.messages.mappers.MessageMapper;
import com.hamsaye.chat.messages.models.MessageEntity;
import com.hamsaye.chat.messages.models.MessageNotification;
import com.hamsaye.chat.messages.models.MessageNotifyType;
import com.hamsaye.chat.messages.services.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageConsumerService {

    private final MessageService messageService;
    private final MessageMapper messageMapper;

    private final SimpMessagingTemplate messagingTemplate;
    private final ResponseMessageMapper mapper;

    @KafkaListener(
            id = "chat-message-listener-id",
            topics = "topic-chat-messages",
            groupId = "group-id",
            containerFactory = "concurrentKafkaListenerContainerFactory"
    )
    public void messageListener(
            MessageNotification notification,
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

    private void sendNewMessage(MessageNotification notification) {

        // send new message
        MessageEntity message = messageMapper.toMessage(notification);

        // send a message to existed conversation
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

    private void sendAvailableMessage(MessageNotification notification) {

        // find the message
        MessageEntity message = messageService.findMessageById(notification.id());

        // return by websocket
        messagingTemplate.convertAndSend(
                "/conversation" + notification.conversationId() + "/queue/messages",
                ResponseEntity.ok(mapper.toResponse(message))
        );
    }
}
