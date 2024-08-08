package com.medilabo.gateway_service.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi customOpenAPI() {
        return GroupedOpenApi.builder()
                .group("custom")
                .addOpenApiCustomizer(openApi -> {
                    openApi.info(new io.swagger.v3.oas.models.info.Info().title("Medilabo Solutions API")
                            .description("Microservices endpoints documentation.")
                            .version("1.0.0"));
                })
                .build();
    }

}
