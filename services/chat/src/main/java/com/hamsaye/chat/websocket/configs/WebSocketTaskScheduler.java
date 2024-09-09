package com.hamsaye.chat.websocket.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * Configuration class for setting up the task scheduler used in the WebSocket chat application.
 * <p>
 * This class configures a {@link TaskScheduler} to handle scheduled tasks related to chat functionality.
 * It sets up a {@link ThreadPoolTaskScheduler} with a pool size of 10 threads, a custom thread name prefix,
 * and sets the threads to be daemon threads.
 * </p>
 * <p>
 * The scheduler is used to manage and execute tasks that may need to run periodically or at specific times
 * within the chat application.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Configuration
@EnableScheduling
public class WebSocketTaskScheduler {

    /**
     * Creates and configures a {@link ThreadPoolTaskScheduler} bean for the chat application.
     *
     * @return a {@link TaskScheduler} instance configured with a pool size of 10 threads and a thread name prefix of "scheduling-"
     */
    @Bean
    public TaskScheduler chatTaskScheduler() {

        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();

        scheduler.setPoolSize(10);
        scheduler.setThreadNamePrefix("scheduling-");
        scheduler.setDaemon(true);

        return scheduler;
    }
}
