package com.ads.report.adapters.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleUnknownRuntime(RuntimeException exception, WebRequest request) {
        return ResponseEntity.internalServerError().body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnknownException(Exception exception, WebRequest request) {
        return ResponseEntity.internalServerError().body(Map.of("error", exception.getMessage()));
    }
}
