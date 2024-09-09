package com.microservices.warehouse.kafka.configs;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.ByteArrayJsonMessageConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration for Kafka consumers.
 *
 * <p>
 * This configuration class sets up Kafka consumer properties, including deserialization settings,
 * and configures a Kafka listener container factory.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Configuration
public class ConsumeKafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrap_server_config;

    /**
     * Creates a map of properties used for Kafka consumer configuration.
     *
     * @return a map containing Kafka consumer properties
     * @since 1.0
     */
    private Map<String, Object> consumeProps() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap_server_config);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 300000);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 45000);
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 3000);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 500);
        return props;
    }

    /**
     * Configures the Kafka consumer factory.
     *
     * @return a {@link ConsumerFactory} instance configured with the properties
     * @since 1.0
     */
    @Bean
    public ConsumerFactory<String, byte[]> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumeProps());
    }

    /**
     * Configures the Kafka listener container factory.
     *
     * @param consumerFactory the consumer factory to use
     * @return a {@link ConcurrentKafkaListenerContainerFactory} instance
     * @since 1.0
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, byte[]> concurrentKafkaListenerContainerFactory(
            ConsumerFactory<String, byte[]> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, byte[]> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);
        factory.setRecordMessageConverter(new ByteArrayJsonMessageConverter());
        return factory;
    }
}
