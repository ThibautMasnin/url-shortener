package com.pct.urlshortener.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(
                new Info()
                        .title("URL Shortener API")
                        .description(
                                "API for shortening URLs and retrieving original URLs. Designed for Portage Cybertech technical test.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Thibaut Masnin")
                                .email("thibautmasnin@gmail.com")));
    }

}
