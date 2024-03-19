package com.example.restproductservice.exception;
/**
 * Исключение, выбрасываемое когда попытка регистрации с уже существующим именем пользователя.
 */
public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String message) {super(message);}
}
