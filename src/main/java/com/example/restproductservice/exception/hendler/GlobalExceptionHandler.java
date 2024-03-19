package com.example.restproductservice.exception.hendler;


import com.example.restproductservice.exception.*;
import com.example.restproductservice.exception.response.CategoryResponseException;
import com.example.restproductservice.exception.response.ProductResponseException;
import com.example.restproductservice.exception.response.UserResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
/**
 * Глобальный обработчик исключений для приложения.
 * Предоставляет централизованное управление исключениями, возникающими во время выполнения запросов.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<CategoryResponseException> handleException(CategoryContainsActiveProductsException ex) {
        CategoryResponseException response = new CategoryResponseException(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<CategoryResponseException> handleException(NotCategoriesException ex) {
        CategoryResponseException response = new CategoryResponseException(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ProductResponseException> handleException(ProductNotFoundException ex) {
        ProductResponseException response = new ProductResponseException(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<CategoryResponseException> handleException(CategoryNotFoundException ex) {
        CategoryResponseException response = new CategoryResponseException(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<UserResponseException> handleException(UserNotFoundException ex) {
        UserResponseException response = new UserResponseException(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<UserResponseException> handleException(UsernameAlreadyExistsException ex) {
        UserResponseException response = new UserResponseException(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<UserResponseException> handleException(InvalidRegistrationDataException ex) {
        UserResponseException response = new UserResponseException(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<UserResponseException> handleException(UsernameNotFoundException ex) {
        UserResponseException response = new UserResponseException(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<UserResponseException> handleException(EmailAlreadyExistsException ex) {
        UserResponseException response = new UserResponseException(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
