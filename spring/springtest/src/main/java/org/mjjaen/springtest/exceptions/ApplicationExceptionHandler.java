package org.mjjaen.springtest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseBody
    public ResponseEntity<CustomErrorResponse> itemNotFoundHandler(ItemNotFoundException ex) {
        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(ex.getMessage());
        errors.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity(errors, HttpStatus.NOT_FOUND);
    }
}