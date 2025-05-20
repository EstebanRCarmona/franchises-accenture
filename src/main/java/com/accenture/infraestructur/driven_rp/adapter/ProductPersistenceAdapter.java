package com.accenture.infraestructur.driven_rp.adapter;

import com.accenture.domain.interfaces.IProductPersistence;
import com.accenture.domain.model.Product;
import com.accenture.infraestructur.driven_rp.entity.ProductEntity;
import com.accenture.infraestructur.driven_rp.mapper.ProductEntityMapper;
import com.accenture.infraestructur.driven_rp.persistence.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements IProductPersistence {
    private final IProductRepository productRepository;
    private final ProductEntityMapper productEntityMapper;

    @Override
    public Mono<Product> saveProduct(Product product) {
        ProductEntity entity = productEntityMapper.toEntity(product);
        return productRepository.save(entity).map(productEntityMapper::toModel);
    }

    @Override
    public Mono<Void> deleteProduct(Long productId) {
        return productRepository.deleteById(productId);
    }

    @Override
    public Mono<Product> findProductById(Long productId) {
        return productRepository.findById(productId).map(productEntityMapper::toModel);
    }

    @Override
    public Flux<Product> findAllProductsByBranch(Long branchId) {
        return productRepository.findAllByBranchId(branchId).map(productEntityMapper::toModel);
    }
}
