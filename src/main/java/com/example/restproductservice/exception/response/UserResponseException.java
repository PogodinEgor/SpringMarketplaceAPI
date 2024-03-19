package com.example.restproductservice.exception.response;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс для формирования ответа об ошибке, связанной с продуктами.
 * Содержит информацию о сообщении ошибки и времени её возникновения.
 */
@Getter
@Setter
public class UserResponseException {
    private String message;
    private String timestamp;

    public UserResponseException(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
