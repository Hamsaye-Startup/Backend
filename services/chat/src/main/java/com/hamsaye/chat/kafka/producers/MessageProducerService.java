package com.hamsaye.chat.kafka.producers;

import com.hamsaye.chat.messages.models.MessageNotification;
import com.hamsaye.chat.users.requests.UserNotifyRequest;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MessageProducerService {

    private static final String DEFAULT_USER_TOPIC = "topic-chat-messages";

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public void send(@Payload MessageNotification payload) {

        // generate the headers
        Map<String, Object> kafkaHeaders = new HashMap<>();
        kafkaHeaders.put(KafkaHeaders.TOPIC, DEFAULT_USER_TOPIC);

        // generate the kafka message
        Message<MessageNotification> message = generateMessage(payload, kafkaHeaders);
        kafkaTemplate.send(message);
    }

    private Message<MessageNotification> generateMessage(
            MessageNotification payload,
            Map<String, Object> headers
    ) {
        return MessageBuilder.createMessage(
                payload,
                new MessageHeaders(headers)
        );
    }

    @Bean
    public NewTopic createMessagesTopic() {
        return TopicBuilder.name(DEFAULT_USER_TOPIC)
                .build();
    }
}
