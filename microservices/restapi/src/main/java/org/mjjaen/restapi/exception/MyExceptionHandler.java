package org.mjjaen.restapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@ControllerAdvice(annotations = RestController.class)
@Slf4j
public class MyExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DataNotFoundException.class)
    public final ResponseEntity<CustomError> handleNotFoundException(DataNotFoundException exception, WebRequest request) {
        log.error(exception.getMessage());
        CustomError error = CustomError.builder().localDateTime(LocalDateTime.now()).message(exception.getMessage()).details(request.getDescription(false)).build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<CustomError> handleMethodArgumentNotValidAtPersistanceLevel(ConstraintViolationException exception, WebRequest request) {
        log.error(exception.getMessage());
        CustomError error = CustomError.builder().localDateTime(LocalDateTime.now()).message(exception.getMessage()).details(request.getDescription(false)).build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest req) {
        log.error(exception.getMessage());
        CustomError error = CustomError.builder().localDateTime(LocalDateTime.now()).message("Validation failed").details(exception.getBindingResult().getAllErrors().toString()).build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<CustomError> handleAllExceptions(Exception exception, WebRequest request) {
        log.error(exception.getMessage());
        CustomError error = CustomError.builder().localDateTime(LocalDateTime.now()).message(exception.getMessage()).details(request.getDescription(false)).build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}