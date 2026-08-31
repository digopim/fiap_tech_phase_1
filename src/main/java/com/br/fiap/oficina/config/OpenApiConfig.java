package com.br.fiap.oficina.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Oficina API - FIAP")
                .description("Phase 1 - API de gestão da oficina com autenticação JWT.")
                .version("1.0.0"));
    }
}
