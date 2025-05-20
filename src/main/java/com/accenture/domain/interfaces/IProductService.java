package com.accenture.domain.interfaces;

import com.accenture.domain.model.PageResponse;
import com.accenture.domain.model.Product;
import com.accenture.domain.model.responses.ProductBranchPair;
import reactor.core.publisher.Mono;

public interface IProductService {
    Mono<Product> addProductToBranch(String name, Integer stock, Long branchId);
    Mono<Void> removeProductFromBranch(Long productId);
    Mono<Product> updateProductStock(Long productId, Integer stock);
    Mono<PageResponse<ProductBranchPair>> getProductsMaxStockByBranchForFranchise(Long franchiseId, int page, int size);
    Mono<PageResponse<Product>> getProductsByBranch(Long branchId, int page, int size);
    Mono<Product> updateProductName(Long productId, String name);
}