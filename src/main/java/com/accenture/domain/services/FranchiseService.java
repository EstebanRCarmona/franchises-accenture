package com.accenture.domain.services;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Service;

import com.accenture.domain.exception.ErrorException;
import com.accenture.domain.exception.ErrorNotFound;
import com.accenture.domain.exception.ExceptionAlreadyExist;
import com.accenture.domain.util.ConstantsDomain;
import com.accenture.domain.interfaces.IFranchisePersistence;
import com.accenture.domain.interfaces.IFranchiseService;
import com.accenture.domain.interfaces.IPaginator;
import com.accenture.domain.model.Franchise;
import com.accenture.domain.model.PageResponse;
import com.accenture.domain.util.validator.ReactiveValidator;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FranchiseService implements IFranchiseService {
    private final IFranchisePersistence franchisePersistence;
    private final IPaginator<Franchise> paginator;

    @Override
    public Mono<Franchise> createFranchise(String name) {
        return ReactiveValidator.validateNotEmpty(name, ConstantsDomain.ERR_NAME_NULL)
            .flatMap(validName -> franchisePersistence.findByName(validName)
                .flatMap(existing -> Mono.<Franchise>error(new ExceptionAlreadyExist(ConstantsDomain.ERR_FRANCHISE_ALREADY_EXISTS)))
                .switchIfEmpty(franchisePersistence.save(new Franchise(null, validName))))
            .timeout(Duration.ofSeconds(3))
            .retry(2)
            .onErrorResume(e -> {
                if (e instanceof TimeoutException) {
                    return Mono.error(new ErrorException(ConstantsDomain.ERR_TIMEOUT_OBTAINING_FRANCHISES));
                }
                return Mono.error(e);
            });
    }

    @Override
    public Mono<Franchise> updateFranchiseName(Long id, String name) {
        return ReactiveValidator.validateNotEmpty(name, ConstantsDomain.ERR_NAME_NULL)
            .flatMap(validName -> ReactiveValidator.validateNotNull(id, ConstantsDomain.ERR_ID_NULL)
                .flatMap(validId -> franchisePersistence.findById(validId)
                    .switchIfEmpty(Mono.error(new ErrorNotFound(ConstantsDomain.ERR_FRANCHISE_NOT_FOUND_ID + validId)))
                    .flatMap(existing -> franchisePersistence.updateName(validId, validName))))
            .timeout(Duration.ofSeconds(3))
            .retry(2)
            .onErrorResume(e -> {
                if (e instanceof TimeoutException) {
                    return Mono.error(new ErrorException(ConstantsDomain.ERR_TIMEOUT_OBTAINING_FRANCHISES));
                }
                return Mono.error(e);
            });
    }

    @Override
    public Mono<PageResponse<Franchise>> getAllFranchisesPaged(int page, int size) {
        return paginator.count()
            .flatMap(total -> paginator.findPage(page, size).collectList()
                .map(content -> new PageResponse<>(content, page, size, total, (int) Math.ceil((double) total / size))))
            .timeout(Duration.ofSeconds(5))
            .retry(3)
            .onErrorResume(e -> {
                if (e instanceof TimeoutException) {
                    return Mono.error(new ErrorException(ConstantsDomain.ERR_TIMEOUT_OBTAINING_FRANCHISES));
                }
                return Mono.error(e);
            });
    }
}
