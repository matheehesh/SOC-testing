package com.lanka.job.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<Map<String, Object>> handle(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(Map.of(
                "error", ex.getReason() == null ? "Request failed" : ex.getReason(),
                "status", ex.getStatusCode().value(),
                "timestamp", LocalDateTime.now().toString()));
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage(), "status", 400, "timestamp", LocalDateTime.now().toString()));
    }
}
