package com.accenture.domain.services;

import com.accenture.domain.exception.ErrorException;
import com.accenture.domain.exception.ErrorNotFound;
import com.accenture.domain.exception.ExceptionAlreadyExist;
import com.accenture.domain.interfaces.IBranchPersistence;
import com.accenture.domain.interfaces.IBranchService;
import com.accenture.domain.interfaces.IFranchisePersistence;
import com.accenture.domain.model.Branch;
import com.accenture.domain.model.PageResponse;
import com.accenture.domain.model.responses.BranchWithFranchise;
import com.accenture.domain.util.ConstantsDomain;
import com.accenture.domain.util.validator.ReactiveValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class BranchService implements IBranchService {
    private final IBranchPersistence branchPersistence;
    private final IFranchisePersistence franchisePersistence;

    @Override
    public Mono<Branch> createBranch(String name, Long franchiseId) {
        return ReactiveValidator.validateNotEmpty(name, ConstantsDomain.ERR_NAME_NULL)
            .flatMap(validName -> ReactiveValidator.validateNotNull(franchiseId, ConstantsDomain.ERR_FRANCHISE_ID_NULL)
                .flatMap(validFranchiseId -> branchPersistence.findByNameIgnoreCase(validName)
                    .flatMap(existing -> Mono.<Branch>error(new ExceptionAlreadyExist(ConstantsDomain.ERR_BRANCH_ALREADY_EXISTS)))
                    .switchIfEmpty(
                        franchisePersistence.findById(validFranchiseId)
                            .switchIfEmpty(Mono.error(new ErrorNotFound(ConstantsDomain.ERR_FRANCHISE_NOT_FOUND + validFranchiseId)))
                            .flatMap(franchise -> branchPersistence.save(new Branch(null, validName, validFranchiseId)))
                    )
                )
            )
            .timeout(Duration.ofSeconds(5))
            .retry(2)
            .onErrorResume(e -> {
                if (e instanceof TimeoutException) {
                    return Mono.error(new ErrorException(ConstantsDomain.ERR_TIMEOUT_OBTAINING_BRANCHES));
                }
                return Mono.error(e);
            });
    }

    @Override
    public Mono<PageResponse<BranchWithFranchise>> getAllBranchesWithFranchisePaged(int page, int size) {
        return branchPersistence.count()
            .flatMap(total -> branchPersistence.findAllPaged(page, size)
                .flatMap(branch -> franchisePersistence.findById(branch.getFranchiseId())
                    .map(franchise -> new BranchWithFranchise(branch, franchise))
                    .onErrorResume(e -> {
                        if (e instanceof ErrorNotFound) {
                            return Mono.empty();
                        }
                        return Mono.error(e);
                    })
                )
                .collectList()
                .map(content -> new PageResponse<>(content, page, size, total, (int) Math.ceil((double) total / size)))
            )
            .timeout(Duration.ofSeconds(5))
            .retry(3)
            .onErrorResume(e -> {
                if (e instanceof TimeoutException) {
                    return Mono.error(new ErrorException(ConstantsDomain.ERR_TIMEOUT_OBTAINING_BRANCHES));
                }
                return Mono.error(e);
            });
    }

    @Override
    public Mono<Branch> updateBranchName(Long id, String name) {
        return ReactiveValidator.validateNotNull(id, ConstantsDomain.ERR_ID_NULL)
            .flatMap(validId -> ReactiveValidator.validateNotEmpty(name, ConstantsDomain.ERR_NAME_NULL)
                .flatMap(validName -> branchPersistence.findById(validId)
                    .switchIfEmpty(Mono.error(new ErrorNotFound(ConstantsDomain.ERR_BRANCH_NOT_FOUND + validId)))
                    .flatMap(branch -> {
                        branch.setName(validName);
                        return branchPersistence.save(branch);
                    })
                )
            )
            .timeout(Duration.ofSeconds(3))
            .retry(2)
            .onErrorResume(e -> {
                if (e instanceof TimeoutException) {
                    return Mono.error(new ErrorException(ConstantsDomain.ERR_TIMEOUT_OBTAINING_BRANCHES));
                }
                return Mono.error(e);
            });
    }
}
