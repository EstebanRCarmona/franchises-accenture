package com.franchises.domain.exception;

public class ErrorBadRequest extends RuntimeException {
    public ErrorBadRequest (String message) {
            super(message);
        }

}
