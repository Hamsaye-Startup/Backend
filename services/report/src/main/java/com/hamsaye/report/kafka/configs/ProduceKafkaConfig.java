package com.hamsaye.report.kafka.configs;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.ByteArrayJsonMessageConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for setting up Kafka producer properties and KafkaTemplate.
 * Defines the necessary beans for producing Kafka messages with String keys and byte array values.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Configuration
public class ProduceKafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrap_server_config;

    /**
     * Provides the configuration properties for the Kafka producer.
     *
     * @return A {@link Map} containing the Kafka producer properties.
     * @since 1.0
     */
    private Map<String, Object> producerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap_server_config);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 10);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
        return props;
    }

    /**
     * Creates a {@link ProducerFactory} bean for Kafka producers.
     *
     * @return A {@link ProducerFactory} instance configured with the Kafka producer properties.
     * @since 1.0
     */
    @Bean
    public ProducerFactory<String, byte[]> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerProps());
    }

    /**
     * Creates a {@link KafkaTemplate} bean for sending Kafka messages.
     * Configures the template with a {@link ByteArrayJsonMessageConverter} for message conversion.
     *
     * @param producerFactory The {@link ProducerFactory} to be used by the {@link KafkaTemplate}.
     * @return A {@link KafkaTemplate} instance configured for producing Kafka messages with String keys and byte array values.
     * @since 1.0
     */
    @Bean
    public KafkaTemplate<String, byte[]> kafkaTemplate(
            ProducerFactory<String, byte[]> producerFactory
    ) {
        KafkaTemplate<String, byte[]> template = new KafkaTemplate<>(producerFactory);
        template.setMessageConverter(new ByteArrayJsonMessageConverter());
        return template;
    }
}
