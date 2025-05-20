package com.accenture.domain.exception;

public class ErrorBadRequest extends RuntimeException {
    public ErrorBadRequest (String message) {
            super(message);
        }

}
