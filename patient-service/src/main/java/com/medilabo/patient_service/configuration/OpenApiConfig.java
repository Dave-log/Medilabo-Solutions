package com.medilabo.patient_service.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Patient API")
                        .version("1.0")
                        .description("This microservice is responsible for managing patient information." +
                                "It provided endpoint to retrieve, create, update and delete patient data."));
    }
}
