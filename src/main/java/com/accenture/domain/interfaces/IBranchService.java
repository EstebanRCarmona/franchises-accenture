package com.accenture.domain.interfaces;

import com.accenture.domain.model.Branch;
import com.accenture.domain.model.PageResponse;
import com.accenture.domain.model.responses.BranchWithFranchise;

import reactor.core.publisher.Mono;

public interface IBranchService {
    Mono<Branch> createBranch(String name, Long franchiseId);
    Mono<PageResponse<BranchWithFranchise>> getAllBranchesWithFranchisePaged(int page, int size);
    Mono<Branch> updateBranchName(Long id, String name);
}
