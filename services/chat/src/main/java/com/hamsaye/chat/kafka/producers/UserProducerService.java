package com.hamsaye.chat.kafka.producers;

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

/**
 * Service for producing and sending Kafka messages related to user notifications.
 * <p>
 * This service handles sending {@link UserNotifyRequest} payloads to Kafka topics. It includes
 * methods for configuring Kafka topics and for sending messages with the appropriate headers.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UserProducerService {

    private static final String DEFAULT_USER_TOPIC = "topic-chat-users";

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    /**
     * Sends a {@link UserNotifyRequest} payload to the default Kafka topic.
     * <p>
     * This method creates a Kafka message with appropriate headers and sends it to the configured
     * Kafka topic using the {@link KafkaTemplate}.
     * </p>
     *
     * @param payload the {@link UserNotifyRequest} payload to be sent
     * @since 1.0
     */
    public void send(@Payload UserNotifyRequest payload) {

        // generate the headers
        Map<String, Object> kafkaHeaders = new HashMap<>();
        kafkaHeaders.put(KafkaHeaders.TOPIC, DEFAULT_USER_TOPIC);

        // generate the kafka message
        Message<UserNotifyRequest> message = generateMessage(payload, kafkaHeaders);
        kafkaTemplate.send(message);
    }

    private Message<UserNotifyRequest> generateMessage(
            UserNotifyRequest payload,
            Map<String, Object> headers
    ) {
        return MessageBuilder.createMessage(
                payload,
                new MessageHeaders(headers)
        );
    }

    /**
     * Creates a new Kafka topic for user notifications.
     *
     * @return the {@link NewTopic} instance for the Kafka topic
     * @since 1.0
     */
    @Bean
    public NewTopic createUsersTopic() {
        return TopicBuilder.name(DEFAULT_USER_TOPIC)
                .build();
    }
}
