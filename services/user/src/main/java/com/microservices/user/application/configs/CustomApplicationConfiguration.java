package com.microservices.user.application.configs;

import com.microservices.user.users.services.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * Configuration class for setting up custom authentication components for the application.
 * <p>
 * This class provides beans for password encoding and authentication provider to integrate
 * with Spring Security's authentication framework. It uses BCrypt for password encoding with a custom
 * seed and configures the DAO authentication provider to use a custom user details service.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 * @since 1.0
 */
@Configuration
@RequiredArgsConstructor
public class CustomApplicationConfiguration {

    private static final String seed = "kLSBacrQaDL8CyVFonUC?ryw@rKI&39D";

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    /**
     * Provides a {@link PasswordEncoder} bean using BCryptPasswordEncoder with a custom seed for entropy.
     * <p>
     * The BCryptPasswordEncoder is configured with BCrypt version $2A and a secure random seed derived
     * from a predefined string. This bean is used for encoding passwords to ensure secure storage and comparison.
     * </p>
     *
     * @return a {@link PasswordEncoder} instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(
                BCryptPasswordEncoder.BCryptVersion.$2A,
                new SecureRandom(seed.getBytes(StandardCharsets.UTF_8))
        );
    }

    /**
     * Provides an {@link AuthenticationProvider} bean using DaoAuthenticationProvider.
     * <p>
     * The DaoAuthenticationProvider is configured with the custom {@link UserDetailsServiceImpl} for loading
     * user-specific data and the {@link PasswordEncoder} for validating passwords. This bean integrates
     * with Spring Security to handle authentication.
     * </p>
     *
     * @return an {@link AuthenticationProvider} instance
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsServiceImpl);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Provides an {@link AuthenticationManager} bean.
     * <p>
     * The AuthenticationManager is obtained from the {@link AuthenticationConfiguration} provided by
     * Spring Security. This manager is used for processing authentication requests.
     * </p>
     *
     * @param configuration the {@link AuthenticationConfiguration} to get the authentication manager from
     * @return an {@link AuthenticationManager} instance
     * @throws Exception if there is an error while getting the authentication manager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
