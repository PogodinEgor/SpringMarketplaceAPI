package com.example.restproductservice.exception;
/**
 * Исключение, выбрасываемое когда категория указана неверно или отсутствует при создании продукта.
 */
public class NotCategoriesException extends RuntimeException{
    public NotCategoriesException(String message) {
        super(message);
    }
}
