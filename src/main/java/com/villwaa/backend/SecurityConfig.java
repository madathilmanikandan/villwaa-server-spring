package com.villwaa.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                // Disable CSRF for H2 console to allow POST requests
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                // Configure authorization rules
                .authorizeHttpRequests(auth -> auth
                        // Allow unauthenticated access to H2 console and its resources
                        .requestMatchers("/h2-console/**").permitAll()
                        // Secure all other endpoints
                        .anyRequest().authenticated()
                )
                // Allow H2 console to work in an iframe
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                // Configure form login
                .formLogin(withDefaults());

        return http.build();
    }
}