package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceInvalidArgumentException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceConflictException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceResourceNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {
    private CustomErrorType defaultCustomErrorTypeConstruct(String message) {
        return new CustomErrorType(LocalDateTime.now(), message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CustomErrorType onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        CustomErrorType customErrorType = defaultCustomErrorTypeConstruct(
                "Validation errors found"
        );
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            customErrorType.getErrors().add(fieldError.getDefaultMessage());
        }
        return customErrorType;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CustomErrorType onConstraintViolation(ConstraintViolationException e) {
        CustomErrorType customErrorType = defaultCustomErrorTypeConstruct(
                "Validation errors found"
        );
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            customErrorType.getErrors().add(violation.getMessage());
        }
        return customErrorType;
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public CustomErrorType onAccessDeniedException(org.springframework.security.access.AccessDeniedException e) {
        return defaultCustomErrorTypeConstruct(
                "Forbidden Exception: User does not have authorization to access this resource."
        );
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public CustomErrorType onResourceNotFoundException(NoHandlerFoundException e) {
        return defaultCustomErrorTypeConstruct(
                "Resource not found: " + e.getRequestURL()
        );
    }

    @ExceptionHandler(SaceInvalidArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CustomErrorType onBadRequestException(SaceInvalidArgumentException e) {
        return defaultCustomErrorTypeConstruct(
                "Bad request" + e.getMessage()
        );
    }

    @ExceptionHandler(SaceResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CustomErrorType onNotFoundException(SaceResourceNotFoundException e) {
        return defaultCustomErrorTypeConstruct(
                "Resource not found: " + e.getMessage()
        );
    }

    @ExceptionHandler(SaceConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public CustomErrorType onConflictException(SaceConflictException e) {
        return defaultCustomErrorTypeConstruct(
                "Application conflict: " + e.getMessage()
        );
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public CustomErrorType onInternalServerError(RuntimeException e) {
        return defaultCustomErrorTypeConstruct("Internal server error occurred: " + e.getMessage());
    }

}