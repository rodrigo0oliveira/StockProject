package br.com.estoque.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductException {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> invalidCodeException(IllegalArgumentException illegalArgumentException){
        String errorMessage = "Código informado já existe";
        return new ResponseEntity<>(errorMessage,HttpStatus.CONFLICT);
    }
}
