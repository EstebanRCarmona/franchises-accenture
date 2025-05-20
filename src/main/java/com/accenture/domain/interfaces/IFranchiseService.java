package com.accenture.domain.interfaces;

import com.accenture.domain.model.Franchise;
import com.accenture.domain.model.PageResponse;

import reactor.core.publisher.Mono;

public interface IFranchiseService {
    Mono<Franchise> createFranchise(String name);
    Mono<Franchise> updateFranchiseName(Long id, String name);
    Mono<PageResponse<Franchise>> getAllFranchisesPaged(int page, int size);
}
