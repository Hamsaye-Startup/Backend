package com.hamsaye.chat.websocket.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.ByteArrayMessageConverter;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON;

/**
 * Configuration class for setting up WebSocket message broker for chat functionality.
 * <p>
 * This class configures WebSocket support with STOMP (Simple Text Oriented Messaging Protocol)
 * and integrates with a message broker to handle chat messaging. It defines endpoints for WebSocket
 * connections, sets message size and buffer limits, configures the message broker registry,
 * and specifies message converters.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketMessageBrokerConfig implements WebSocketMessageBrokerConfigurer {

    private final TaskScheduler chatTaskScheduler;

    // authentication process by header
    /*private final WebSocketChannelInterceptor channelInterceptor;

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(channelInterceptor);
    }*/

    /**
     * Registers STOMP endpoints for WebSocket connections.
     * <p>
     * The endpoint "/ws" is exposed for WebSocket connections and allows cross-origin requests
     * from any origin. The SockJS fallback option is enabled for clients that do not support WebSocket.
     * </p>
     *
     * @param registry the {@link StompEndpointRegistry} to register endpoints with
     * @since 1.0
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.setPreserveReceiveOrder(true);
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    /**
     * Configures WebSocket transport settings including message size and buffer size limits.
     * <p>
     * Sets the maximum message size and buffer size to 25KB to ensure that large messages do not
     * overwhelm the system.
     * </p>
     *
     * @param registry the {@link WebSocketTransportRegistration} to configure
     * @since 1.0
     */
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.setMessageSizeLimit(25 * 1024); // message size limit = 25KB
        registry.setSendBufferSizeLimit(25 * 1024); // buffer size limit = 25KB
    }

    /**
     * Configures the message broker for handling chat messaging.
     * <p>
     * Sets up an in-memory message broker with simple broker endpoints for queue and topic destinations.
     * Configures heartbeat intervals and assigns the provided {@link TaskScheduler} for scheduling
     * heartbeats and other tasks.
     * </p>
     *
     * @param registry the {@link MessageBrokerRegistry} to configure
     * @since 1.0
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        registry.enableSimpleBroker("/queue", "/topic")
                .setHeartbeatValue(new long[]{10000, 20000})
                .setTaskScheduler(this.chatTaskScheduler);

        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }

    /**
     * Configures message converters for handling different message types.
     * <p>
     * Adds converters for JSON, String, and byte array message types. Configures a {@link MappingJackson2MessageConverter}
     * to handle JSON serialization and deserialization with customized {@link ObjectMapper} for handling LocalDateTime
     * types and disabling default date serialization as timestamps.
     * </p>
     *
     * @param messageConverters the list of {@link MessageConverter} instances to configure
     * @return false to allow further message converter configuration
     * @since 1.0
     */
    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
        resolver.setDefaultMimeType(APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        objectMapper.registerModule(javaTimeModule);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(objectMapper);
        converter.setContentTypeResolver(resolver);

        messageConverters.add(new StringMessageConverter());
        messageConverters.add(new ByteArrayMessageConverter());
        messageConverters.add(converter);
        return false;
    }
}
