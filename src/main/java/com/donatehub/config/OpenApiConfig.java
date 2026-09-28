package com.donatehub.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI donateHubOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("DonateHub API")
                        .description("Old Clothes and Book Donation Drive Tracker")
                        .version("1.0.0"));
    }
}
