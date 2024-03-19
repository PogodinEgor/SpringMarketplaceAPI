package com.example.restproductservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * Класс ModelMapperConfig предназначен для конфигурации и предоставления экземпляра ModelMapper в качестве бина Spring.
 * ModelMapper — это библиотека, которая автоматически отображает одни объекты в другие, упрощая преобразование и передачу данных
 * между различными слоями приложения, например, из сущностей в DTO (Data Transfer Object) и обратно.
 * Эта конфигурация позволяет автоматически внедрять ModelMapper в компоненты Spring для использования в приложении.
 */
@Configuration
public class ModelMapperConfig {

    /**
     * Создаёт и предоставляет бин ModelMapper.
     * ModelMapper используется для автоматического и гибкого отображения между классами модели данных и DTO,
     * минимизируя необходимость ручного преобразования данных.
     *
     * @return экземпляр ModelMapper для использования в приложении.
     */
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
