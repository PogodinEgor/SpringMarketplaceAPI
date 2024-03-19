package com.example.restproductservice.exception;
/**
 * Исключение, выбрасываемое когда попытка удалить категорию содержащую активные продукты.
 */
public class CategoryContainsActiveProductsException extends RuntimeException{
    public CategoryContainsActiveProductsException(String message) {
        super(message);
    }
}
