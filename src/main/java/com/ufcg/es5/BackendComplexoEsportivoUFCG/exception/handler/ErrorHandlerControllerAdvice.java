package com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.handler;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceInternalException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceInvalidArgumentException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceConflictException;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.exception.common.SaceResourceNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;

@ControllerAdvice
public class ErrorHandlerControllerAdvice {
    private CustomErrorType defaultCustomErrorTypeConstruct(String message) {
        return new CustomErrorType(LocalDateTime.now(), message);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
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

    @ExceptionHandler(value = ConstraintViolationException.class)
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

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public CustomErrorType onAccessDeniedException(AccessDeniedException e) {
        return defaultCustomErrorTypeConstruct(
                "Forbidden Exception: User does not have authorization to access this resource."
        );
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
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

    @ExceptionHandler(value = SaceResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CustomErrorType onNotFoundException(SaceResourceNotFoundException e) {
        return defaultCustomErrorTypeConstruct(
                "Resource not found: " + e.getMessage()
        );
    }

    @ExceptionHandler(value = SaceInternalException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public CustomErrorType onInternalException(SaceInternalException e) {
        return defaultCustomErrorTypeConstruct(
                "Internal Error: " + e.getMessage()
        );
    }

    @ExceptionHandler(value = SaceConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public CustomErrorType onConflictException(SaceConflictException e) {
        return defaultCustomErrorTypeConstruct(
                "Application conflict: " + e.getMessage()
        );
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public CustomErrorType onInternalServerError(RuntimeException e) {
        return defaultCustomErrorTypeConstruct("Internal server error occurred: " + e.getMessage());
    }

}