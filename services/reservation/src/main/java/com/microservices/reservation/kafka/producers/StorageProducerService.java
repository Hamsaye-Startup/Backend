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

@Service
@RequiredArgsConstructor
public class StorageProducerService {

    private static final String DEFAULT_USER_TOPIC = "storage-reservation";

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public void send(@Payload ReservationNotifyRequest payload) {

        // generate the headers
        Map<String, Object> kafkaHeaders = new HashMap<>();
        kafkaHeaders.put(KafkaHeaders.TOPIC, DEFAULT_USER_TOPIC);

        // generate the kafka message
        Message<ReservationNotifyRequest> message = generateMessage(payload, kafkaHeaders);
        kafkaTemplate.send(message);
    }

    private Message<ReservationNotifyRequest> generateMessage(
            ReservationNotifyRequest payload,
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
