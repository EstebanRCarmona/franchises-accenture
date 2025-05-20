package com.accenture.domain.interfaces;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPaginator<T> {
    Mono<Long> count();
    Flux<T> findPage(int page, int size);
}
