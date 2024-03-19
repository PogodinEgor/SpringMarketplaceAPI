package com.example.restproductservice.exception;
/**
 * Исключение, выбрасываемое когда пользователь не найден.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {super(message);}
}
