package com.franchises.domain.util.validator;

import reactor.core.publisher.Mono;
import java.util.function.Predicate;

import com.franchises.domain.exception.ErrorBadRequest;

public class ReactiveValidator {

    private ReactiveValidator() {
    }

    public static <T> Mono<T> validate(T value, Predicate<T> predicate, String errorMessage) {
        if (value == null) {
            return Mono.error(new ErrorBadRequest(errorMessage));
        }
        
        return Mono.just(value)
                .filter(predicate)
                .switchIfEmpty(Mono.error(new ErrorBadRequest(errorMessage)));
    }

    
    public static Mono<String> validateNotEmpty(String value, String errorMessage) {
        return validate(value, v -> v != null && !v.isEmpty(), errorMessage);
    }

    public static <T> Mono<T> validateNotNull(T value, String errorMessage) {
        return validate(value, v -> v != null, errorMessage);
    }
}
