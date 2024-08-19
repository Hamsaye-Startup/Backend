package com.hamsaye.chat.messages.mappers;

import com.hamsaye.chat.messages.models.MessageEntity;
import com.hamsaye.chat.messages.models.MessageNotification;
import com.hamsaye.chat.messages.models.MessageNotifyType;
import com.hamsaye.chat.messages.requests.MessageRequest;
import org.springframework.stereotype.Service;

@Service
public class MessageMapper {

    public MessageEntity toMessage(MessageRequest request) {
        return MessageEntity.builder()
                .senderId(request.senderId())
                .conversationId(request.conversationId())
                .content(request.content())
                .build();
    }

    public MessageEntity toMessage(MessageNotification notification) {
        return MessageEntity.builder()
                .senderId(notification.senderId())
                .conversationId(notification.conversationId())
                .content(notification.content())
                .build();
    }
}
