package com.accenture.domain.interfaces;
import com.accenture.domain.model.Franchise;

import reactor.core.publisher.Mono;

public interface IFranchisePersistence {
    Mono<Franchise> save(Franchise franchise);
    Mono<Franchise> updateName(Long id, String name);
    Mono<Franchise> findByName(String name);
    Mono<Franchise> findById(Long id);
}
