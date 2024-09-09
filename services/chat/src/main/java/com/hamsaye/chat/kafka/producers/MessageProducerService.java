package com.hamsaye.chat.kafka.producers;

import com.hamsaye.chat.kafka.requests.MessageNotifyRequest;
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
 * Service for producing and sending Kafka messages related to chat messages.
 * <p>
 * This service is responsible for sending {@link MessageNotifyRequest} payloads to Kafka topics.
 * It includes methods to configure Kafka topics and to send messages with appropriate headers.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class MessageProducerService {

    private static final String DEFAULT_USER_TOPIC = "topic-chat-messages";

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    /**
     * Sends a {@link MessageNotifyRequest} payload to the default Kafka topic.
     * <p>
     * This method creates a Kafka message with appropriate headers and sends it to the configured
     * Kafka topic using the {@link KafkaTemplate}.
     * </p>
     *
     * @param payload the {@link MessageNotifyRequest} payload to be sent
     * @since 1.0
     */
    public void send(@Payload MessageNotifyRequest payload) {

        // generate the headers
        Map<String, Object> kafkaHeaders = new HashMap<>();
        kafkaHeaders.put(KafkaHeaders.TOPIC, DEFAULT_USER_TOPIC);

        // generate the kafka message
        Message<MessageNotifyRequest> message = generateMessage(payload, kafkaHeaders);
        kafkaTemplate.send(message);
    }

    private Message<MessageNotifyRequest> generateMessage(
            MessageNotifyRequest payload,
            Map<String, Object> headers
    ) {
        return MessageBuilder.createMessage(
                payload,
                new MessageHeaders(headers)
        );
    }

    /**
     * Creates a new Kafka topic for chat messages.
     *
     * @return the {@link NewTopic} instance for the Kafka topic
     * @since 1.0
     */
    @Bean
    public NewTopic createMessagesTopic() {
        return TopicBuilder.name(DEFAULT_USER_TOPIC)
                .build();
    }
}
