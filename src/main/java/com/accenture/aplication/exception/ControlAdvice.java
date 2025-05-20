package com.accenture.aplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import com.accenture.domain.exception.ErrorBadRequest;
import com.accenture.domain.exception.ErrorException;
import com.accenture.domain.exception.ErrorNotFound;
import com.accenture.domain.exception.ExceptionAlreadyExist;

import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControlAdvice {

    private Mono<ResponseEntity<ExceptionResponse>> buildErrorResponse(HttpStatus status, String message, ServerWebExchange exchange) {
        return Mono.fromCallable(() -> {
            String path = exchange.getRequest().getPath().toString();
            String method = exchange.getRequest().getMethod().name();
            String timestamp = Instant.now().toString();
            
            ExceptionResponse response = new ExceptionResponse(
                    status.value(),
                    message,
                    path,
                    method,
                    timestamp
            );
            
            return ResponseEntity.status(status).body(response);
        });
    }

    @ExceptionHandler(ErrorBadRequest.class)
    public Mono<ResponseEntity<ExceptionResponse>> handleBadRequest(ErrorBadRequest ex, ServerWebExchange exchange) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), exchange);
    }

    @ExceptionHandler(ExceptionAlreadyExist.class)
    public Mono<ResponseEntity<ExceptionResponse>> handleAlreadyExist(ExceptionAlreadyExist ex, ServerWebExchange exchange) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), exchange);
    }

    @ExceptionHandler(ErrorNotFound.class)
    public Mono<ResponseEntity<ExceptionResponse>> handleNotFound(ErrorNotFound ex, ServerWebExchange exchange) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), exchange);
    }

    @ExceptionHandler(ErrorException.class)
    public Mono<ResponseEntity<ExceptionResponse>> handleGenericError(ErrorException ex, ServerWebExchange exchange) {
        return buildErrorResponse(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage(), exchange);
    }
    

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ExceptionResponse>> handleValidationErrors(WebExchangeBindException ex, ServerWebExchange exchange) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));
        
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Error de validación: " + errorMessage, exchange);
    }
    

    @ExceptionHandler(ResponseStatusException.class)
    public Mono<ResponseEntity<ExceptionResponse>> handleResponseStatusException(ResponseStatusException ex, ServerWebExchange exchange) {
        return buildErrorResponse(ex.getStatusCode().is4xxClientError() 
                ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR, 
                ex.getReason(), 
                exchange);
    }
    
    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ExceptionResponse>> handleAllExceptions(Exception ex, ServerWebExchange exchange) {
        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "Error interno del servidor: " + ex.getMessage(), 
                exchange
        );
    }
}
