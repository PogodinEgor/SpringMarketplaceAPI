package com.example.restproductservice.exception;
/**
 * Исключение, выбрасываемое когда попытка регистрации с уже существующим email.
 */
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {super(message);}
}
