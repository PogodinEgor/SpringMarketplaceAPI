package com.example.restproductservice.exception;
/**
 * Исключение, выбрасываемое когда данные регистрации некорректны или неполны.
 */
public class InvalidRegistrationDataException extends RuntimeException {
    public InvalidRegistrationDataException(String message) {super(message);}
}