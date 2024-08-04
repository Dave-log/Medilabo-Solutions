package com.medilabo.diabetesreportservice.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl("http://gateway-service:8080")
                .build();
    }
}
