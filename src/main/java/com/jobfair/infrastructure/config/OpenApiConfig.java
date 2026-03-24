package com.jobfair.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI jobFairOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("JobFAIR API")
                        .description("Clean Spring Boot CRUD template focused on the Example entity and reusable generic service/controller architecture")
                        .version("v1")
                        .contact(new Contact().name("JobFAIR Team").email("jobfair@example.com"))
                        .license(new License().name("Internal Use")));
    }
}
