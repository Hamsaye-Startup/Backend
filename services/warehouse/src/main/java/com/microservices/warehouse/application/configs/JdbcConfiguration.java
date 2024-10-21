package com.microservices.warehouse.application.configs;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Configuration class for setting up JDBC components using HikariCP as the connection pool.
 * <p>
 * This class provides beans for configuring a HikariDataSource for connection pooling and a JdbcTemplate
 * for interacting with the database. The HikariDataSource is configured using properties from the application
 * configuration file under the prefix "spring.datasource.hikari".
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class JdbcConfiguration {

    /**
     * Provides a {@link HikariDataSource} bean configured with properties from the application configuration.
     * <p>
     * The HikariDataSource is set up using the configuration properties prefixed with "spring.datasource.hikari".
     * This data source is used for managing database connections with efficient connection pooling.
     * </p>
     *
     * @return a {@link HikariDataSource} instance
     */
    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    public HikariDataSource hikariDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    /**
     * Provides a {@link JdbcTemplate} bean for interacting with the database.
     * <p>
     * The JdbcTemplate is configured to use the provided {@link HikariDataSource} for database operations.
     * This template simplifies the use of JDBC and helps to execute queries and updates more easily.
     * </p>
     *
     * @param hikariDataSource the {@link HikariDataSource} to use for the JdbcTemplate
     * @return a {@link JdbcTemplate} instance
     */
    @Bean
    public JdbcTemplate jdbcTemplate(HikariDataSource hikariDataSource) {
        return new JdbcTemplate(hikariDataSource);
    }
}
