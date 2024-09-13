package com.hamsaye.report.kafka.producers;

import com.hamsaye.report.kafka.requests.ViolationNotifyRequest;
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
public class ViolationProducerService {

    public static final String DISTRIBUTE_VIOLATION_TOPIC = "topic-distribute-registered-violation";

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    /**
     * Sends a {@link ViolationNotifyRequest} payload to the default Kafka topic.
     * <p>
     * This method creates a Kafka message with appropriate headers and sends it to the configured
     * Kafka topic using the {@link KafkaTemplate}.
     * </p>
     *
     * @param payload the {@link ViolationNotifyRequest} payload to be sent
     * @since 1.0
     */
    public void send(@Payload ViolationNotifyRequest payload, String topic) {

        // generate the headers
        Map<String, Object> kafkaHeaders = new HashMap<>();
        kafkaHeaders.put(KafkaHeaders.TOPIC, topic);

        // generate the kafka message
        Message<ViolationNotifyRequest> message = generateMessage(payload, kafkaHeaders);
        kafkaTemplate.send(message);
    }

    private Message<ViolationNotifyRequest> generateMessage(
            ViolationNotifyRequest payload,
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
    public NewTopic createDistributeViolationTopic() {
        return TopicBuilder.name(DISTRIBUTE_VIOLATION_TOPIC)
                .build();
    }
}
