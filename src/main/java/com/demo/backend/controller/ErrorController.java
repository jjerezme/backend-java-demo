package com.demo.backend.controller;

import java.util.HashMap;
import java.util.Map;

import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.demo.backend.dto.ErrorDTO;
import com.demo.backend.dto.ResponseDTO;
import com.demo.backend.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

/**
 * Class for error controller
 */
@Slf4j
@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> exceptionHandler(MethodArgumentNotValidException ex) {
        log.error("Method Argument Not Valid Exception Handler", ex);

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        String arguments = errors.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("; "));

        ErrorDTO er = ErrorDTO.request(arguments);
        return new ResponseEntity<>(
                ResponseDTO.error(er),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ResponseDTO> exceptionHandler(IllegalArgumentException ex) {
        log.error("Illegal Argument Exception Handler", ex);

        ErrorDTO er = ErrorDTO.request(ex.getMessage());
        return new ResponseEntity<>(
                ResponseDTO.error(er),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = DataAccessException.class)
    public ResponseEntity<ResponseDTO> exceptionHandler(DataAccessException ex) {
        log.error("Data Access Exception Handler", ex);

        ErrorDTO er = ErrorDTO.request(ex.getMessage());
        return new ResponseEntity<>(
                ResponseDTO.error(er),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ResponseDTO> exceptionHandler(BusinessException ex) {
        log.error("Business Exception Handler", ex);

        return new ResponseEntity<>(
                ResponseDTO.error(ErrorDTO.business(ex)),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ResponseDTO> exceptionHandler(RuntimeException ex) {
        log.error("Runtime Exception Handler", ex);

        return new ResponseEntity<>(
                ResponseDTO.error(ErrorDTO.runtime(ex)),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
