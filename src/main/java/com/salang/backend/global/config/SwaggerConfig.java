package com.salang.backend.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .version("1.0") // 버전
                .title("Salang API") // 이름
                .description("Salang의 백엔드 API 문서"); // 설명
        return new OpenAPI().info(info);
    }
}
