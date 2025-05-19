package com.franchises.infraestructur.driven_rp.persistence;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import org.springframework.stereotype.Repository;

import com.franchises.infraestructur.driven_rp.entity.ProductEntity;

@Repository
public interface IProductRepository extends ReactiveCrudRepository<ProductEntity, Long> {
    Flux<ProductEntity> findAllByBranchId(Long branchId);
}