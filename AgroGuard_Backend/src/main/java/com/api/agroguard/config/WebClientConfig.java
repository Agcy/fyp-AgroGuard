package com.api.agroguard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        System.out.println("Creating web client");
        return WebClient.builder()
                .baseUrl("https://data.nasdaq.com")
                .build();
    }
}
