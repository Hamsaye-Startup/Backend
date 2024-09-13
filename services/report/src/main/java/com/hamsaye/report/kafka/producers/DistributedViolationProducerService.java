package com.hamsaye.report.kafka.producers;

import com.hamsaye.report.kafka.requests.DistributedViolationNotifyRequest;
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
public class DistributedViolationProducerService {


    public static final String REFER_VIOLATION_AGENT_TOPIC = "topic-violation-agent-changed";

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    /**
     * Sends a {@link DistributedViolationNotifyRequest} payload to the default Kafka topic.
     * <p>
     * This method creates a Kafka message with appropriate headers and sends it to the configured
     * Kafka topic using the {@link KafkaTemplate}.
     * </p>
     *
     * @param payload the {@link DistributedViolationNotifyRequest} payload to be sent
     * @since 1.0
     */
    public void send(@Payload DistributedViolationNotifyRequest payload, String topic) {

        // generate the headers
        Map<String, Object> kafkaHeaders = new HashMap<>();
        kafkaHeaders.put(KafkaHeaders.TOPIC, topic);

        // generate the kafka message
        Message<DistributedViolationNotifyRequest> message = generateMessage(payload, kafkaHeaders);
        kafkaTemplate.send(message);
    }

    private Message<DistributedViolationNotifyRequest> generateMessage(
            DistributedViolationNotifyRequest payload,
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
    public NewTopic createChangingViolationAgentTopic() {
        return TopicBuilder.name(REFER_VIOLATION_AGENT_TOPIC)
                .build();
    }
}
