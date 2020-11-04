package com.company.api.user.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AppExceptionHandler {

    //TODO: map exceptions that can be thrown by the application to return a user friendly error response
    @ExceptionHandler({})
    public final ResponseEntity<String> handleException(Exception ex, NativeWebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}