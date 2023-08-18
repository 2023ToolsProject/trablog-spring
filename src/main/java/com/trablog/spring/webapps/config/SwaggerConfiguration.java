package com.trablog.spring.webapps.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Springdoc 테스트")
                .version("2.0.0")
                .description("Springdoc을 사용한 Swagger UI 테스트");
        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
