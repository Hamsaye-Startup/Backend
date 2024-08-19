package com.microservices.user.kafka.producers;

import com.microservices.user.customers.requests.CustomerNotifyRequest;
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
public class CustomerProducerService {

    private static final String DEFAULT_TOPIC = "topic-general-customer-details";

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public void send(@Payload CustomerNotifyRequest payload) {

        // generate the headers
        Map<String, Object> kafkaHeaders = new HashMap<>();
        kafkaHeaders.put(KafkaHeaders.TOPIC, DEFAULT_TOPIC);

        // generate the kafka message
        Message<CustomerNotifyRequest> message = generateMessage(payload, kafkaHeaders);
        kafkaTemplate.send(message);
    }

    private Message<CustomerNotifyRequest> generateMessage(
            CustomerNotifyRequest payload,
            Map<String, Object> headers
    ) {
        return MessageBuilder.createMessage(
                payload,
                new MessageHeaders(headers)
        );
    }

    @Bean
    public NewTopic createCustomerTopic() {
        return TopicBuilder.name(DEFAULT_TOPIC)
                .build();
    }
}
