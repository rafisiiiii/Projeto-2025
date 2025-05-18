package com.projeto2025.api_projeto.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .components(new Components()
                .addSecuritySchemes("bearer-key",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")))
            .info(new Info()
                .title("Sistema de Cadastro de Clientes")
                .version("1.0")
                .description("API para gestão de clientes com integração de CNPJ")
                .contact(new io.swagger.v3.oas.models.info.Contact()
                    .name("Projeto 2025")
                    .email("rafaella.isidoro@fatec.sp.gov.br")));
    }
}

