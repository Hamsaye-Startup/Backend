package com.microservices.reservation.kafka.producers;

import com.microservices.reservation.kafka.requests.ReservationNotifyRequest;
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
 * Service for producing and sending Kafka messages related to storage reservations.
 * This service handles the creation and sending of messages to the Kafka topic
 * designated for storage reservation notifications. It uses {@link KafkaTemplate}
 * to send messages and creates the necessary Kafka topic if it does not already exist.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class StorageProducerService {

    private static final String DEFAULT_USER_TOPIC = "storage-reservation";

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    /**
     * Sends a {@link ReservationNotifyRequest} payload as a Kafka message.
     * This method generates the necessary Kafka headers and message, then sends
     * the message to the configured Kafka topic.
     *
     * @param payload The {@link ReservationNotifyRequest} payload to be sent.
     */
    public void send(@Payload ReservationNotifyRequest payload) {

        // generate the headers
        Map<String, Object> kafkaHeaders = new HashMap<>();
        kafkaHeaders.put(KafkaHeaders.TOPIC, DEFAULT_USER_TOPIC);

        // generate the kafka message
        Message<ReservationNotifyRequest> message = generateMessage(payload, kafkaHeaders);
        kafkaTemplate.send(message);
    }

    /**
     * Creates a {@link Message} with the specified payload and headers.
     *
     * @param payload The payload to include in the message.
     * @param headers The headers to include in the message.
     * @return A {@link Message} instance containing the payload and headers.
     */
    private Message<ReservationNotifyRequest> generateMessage(
            ReservationNotifyRequest payload,
            Map<String, Object> headers
    ) {
        return MessageBuilder.createMessage(
                payload,
                new MessageHeaders(headers)
        );
    }

    /**
     * Creates a Kafka topic for storing reservation messages.
     * This bean defines the topic that messages will be sent to. The topic will be
     * created if it does not already exist.
     *
     * @return A {@link NewTopic} instance for the Kafka topic.
     */
    @Bean
    public NewTopic createMessagesTopic() {
        return TopicBuilder.name(DEFAULT_USER_TOPIC)
                .build();
    }
}
