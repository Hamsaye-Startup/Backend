package com.hamsaye.chat.kafka.consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class TestListener {

    @KafkaListener(id = "test-listener", topics = "test-topic")
    public void listener(String message) {
        System.out.println("This message is received by kafka listener annotation: " + message);
    }

    @KafkaListener(
            id = "test-listener-2",
            topicPartitions = @TopicPartition(
                    topic = "test-topic-2",
                    partitions = {"0", "1"},
                    partitionOffsets = @PartitionOffset(partition = "*", initialOffset = "0")
            )
    )
    public void listener2(
            String message,
            @Header(name = KafkaHeaders.RECEIVED_KEY, required = false) String key
    ) {
        System.out.println("This message is received by kafka listener 2 annotation (key '" + key + "'): " + message);
    }
}
