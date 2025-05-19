package com.franchises.domain.exception;

public class ErrorNotFound extends RuntimeException {
    public ErrorNotFound(String message) {
        super(message);
    }
}
