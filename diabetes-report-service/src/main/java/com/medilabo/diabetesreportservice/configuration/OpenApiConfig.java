package com.medilabo.diabetesreportservice.configuration;

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
                        .title("Diabetes Report API")
                        .version("1.0")
                        .description("This microservice is responsible for assessing the risk of diabetes in patients."));
    }
}
