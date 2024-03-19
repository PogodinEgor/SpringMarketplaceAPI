package com.example.restproductservice.exception.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс для формирования ответа об ошибке, содержащий сообщение об ошибке и временную метку.
 */
@Getter
@Setter
public class CategoryResponseException {
    private String message;
    private String timestamp;

    public CategoryResponseException(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
