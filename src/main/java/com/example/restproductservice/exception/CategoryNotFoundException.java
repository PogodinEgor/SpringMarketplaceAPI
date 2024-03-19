package com.example.restproductservice.exception;
/**
 * Исключение, выбрасываемое когда категория не найдена.
 */
public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
