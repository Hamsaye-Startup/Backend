package com.microservices.warehouse.kafka.configs;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration for Kafka Admin client.
 *
 * <p>
 * This configuration class sets up the Kafka Admin client properties, which are used for
 * administrative operations such as managing Kafka topics.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Configuration
@EnableKafka
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrap_server_config;

    /**
     * Configures the Kafka Admin client with the necessary properties.
     *
     * @return a {@link KafkaAdmin} instance configured with the properties
     * @since 1.0
     */
    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap_server_config);
        // configs.put(AdminClientConfig.SEND_BUFFER_CONFIG, 1024 * 1024); // default 128 * 1024
        return new KafkaAdmin(configs);
    }
}
