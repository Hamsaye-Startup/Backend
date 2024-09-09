package com.hamsaye.chat.messages.mappers;

import com.hamsaye.chat.messages.models.MessageEntity;
import com.hamsaye.chat.kafka.requests.MessageNotifyRequest;
import com.hamsaye.chat.messages.requests.MessageRequest;
import org.springframework.stereotype.Service;

/**
 * Service class for mapping message-related data between different representations.
 * <p>
 * This class provides methods to map data from {@link MessageRequest} and {@link MessageNotifyRequest}
 * to {@link MessageEntity}. It facilitates the conversion of incoming message requests and notifications
 * into the entity representation used for persistence and other operations.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
public class MessageMapper {

    /**
     * Maps a {@link MessageRequest} to a {@link MessageEntity}.
     * <p>
     * This method converts the data from a message request, including sender ID, conversation ID, and content,
     * into a {@link MessageEntity} which can be used for further processing or persistence.
     * </p>
     *
     * @param request the message request to be mapped
     * @return a {@link MessageEntity} containing the data from the request
     * @since 1.0
     */
    public MessageEntity toMessage(MessageRequest request) {
        return MessageEntity.builder()
                .senderId(request.senderId())
                .conversationId(request.conversationId())
                .content(request.content())
                .build();
    }

    /**
     * Maps a {@link MessageNotifyRequest} to a {@link MessageEntity}.
     * <p>
     * This method converts the data from a message notification, including sender ID, conversation ID, and content,
     * into a {@link MessageEntity} which can be used for further processing or persistence.
     * </p>
     *
     * @param notification the message notification to be mapped
     * @return a {@link MessageEntity} containing the data from the notification
     * @since 1.0
     */
    public MessageEntity toMessage(MessageNotifyRequest notification) {
        return MessageEntity.builder()
                .senderId(notification.senderId())
                .conversationId(notification.conversationId())
                .content(notification.content())
                .build();
    }
}
