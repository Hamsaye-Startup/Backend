package com.microservices.user.kafka.producers;

import com.microservices.user.kafka.requests.UserNotifyRequest;
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
 * Service class responsible for producing messages to a Kafka topic for user notifications.
 *
 * This service handles the creation of Kafka messages and sends them to a specified Kafka topic.
 * It also ensures that the Kafka topic is created if it does not exist.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UserProducerService {

    private static final String DEFAULT_TOPIC = "topic-general-customers";

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    /**
     * Sends a message to the Kafka topic with the specified payload.
     *
     * @param payload the {@link UserNotifyRequest} to be sent as a Kafka message.
     * @since 1.0
     */
    public void send(@Payload UserNotifyRequest payload) {

        // generate the headers
        Map<String, Object> kafkaHeaders = new HashMap<>();
        kafkaHeaders.put(KafkaHeaders.TOPIC, DEFAULT_TOPIC);

        // generate the Kafka message
        Message<UserNotifyRequest> message = generateMessage(payload, kafkaHeaders);
        kafkaTemplate.send(message);
    }

    /**
     * Creates a Kafka message with the given payload and headers.
     *
     * @param payload the payload to be included in the Kafka message.
     * @param headers the headers to be included in the Kafka message.
     * @return a {@link Message} with the specified payload and headers.
     * @since 1.0
     */
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
     * Bean definition for creating a Kafka topic if it does not already exist.
     *
     * @return a {@link NewTopic}
     * @since 1.0
     */
    @Bean
    public NewTopic createUserTopic() {
        return TopicBuilder.name(DEFAULT_TOPIC)
                .build();
    }
}
