package com.example.restproductservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * Класс OpenApiConfig настраивает документацию API, используя OpenAPI 3.0.
 * Определяет компоненты безопасности для аутентификации через JWT (JSON Web Tokens)
 * и общую информацию об API, такую как название и описание.
 * Это обеспечивает интеграцию с инструментами, такими как Swagger UI, для визуализации и взаимодействия с API.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Создаёт и возвращает настроенный экземпляр OpenAPI для документации API.
     * Включает в себя настройки компонентов безопасности для аутентификации через JWT,
     * добавляет требование безопасности к операциям API и предоставляет общую информацию о сервисе.
     *
     * @return настроенный объект OpenAPI для использования с Swagger и другими инструментами OpenAPI.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                )
                .addSecurityItem(
                        new SecurityRequirement().addList("bearerAuth")
                )
                .info(new Info().title("ProdCategoryService API")
                                .description("API documentation for ProdCategoryService")

                );
    }
}
