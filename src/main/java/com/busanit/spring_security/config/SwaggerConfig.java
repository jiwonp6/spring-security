package com.busanit.spring_security.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
                                new Info()
                                    .title("스프링부트 REST API")
                                    .version("1.0")
                                    .description("부산 IT 아카데미에서 만들어본 REST API Server")
                             );
    }
}
