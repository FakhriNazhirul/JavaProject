package com.project.organix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Mematikan proteksi CSRF agar testing API mudah
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // MENGIZINKAN SEMUA AKSES tanpa login
            );
        return http.build();
    }
}