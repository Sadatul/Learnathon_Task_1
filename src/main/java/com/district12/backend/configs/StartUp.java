package com.district12.backend.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class StartUp {

    @Value("${spring.profiles.active:dev}")
    private String activeProfile;

    @Bean
    public CommandLineRunner init() {
        return args -> log.info("Application started in {} profile", activeProfile);
    }
}
