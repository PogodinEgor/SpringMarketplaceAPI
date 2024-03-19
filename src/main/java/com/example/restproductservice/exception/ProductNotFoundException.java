package com.example.restproductservice.exception;
/**
 * Исключение, выбрасываемое когда продукт не найден.
 */
public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message) {
        super(message);
    }
}
