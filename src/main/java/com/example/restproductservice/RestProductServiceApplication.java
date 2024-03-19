package com.example.restproductservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "ProdCategoryService",
                description = "Микросервис 'ProdCategoryService' предназначен для управления каталогом продуктов и категорий. " +
                        "Он позволяет создавать, удалять, редактировать и искать продукты и категории. " +
                        "Каждый продукт содержит информацию о наименовании, описании, цене, изображении, категории, дате добавления в каталог и статусе активности. " +
                        "Создание продукта без назначения категории не возможно. ",
                version = "0.0.1",
                contact = @Contact(
                        name = "Pogodin Egor",
                        email = "666deadkain999@gmail.com",
                        url = "https://github.com/PogodinEgor"
                )
        )
)
public class RestProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestProductServiceApplication.class, args);
    }

}
