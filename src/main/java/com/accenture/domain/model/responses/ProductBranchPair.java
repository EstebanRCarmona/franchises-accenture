package com.accenture.domain.model.responses;

import com.accenture.domain.model.Product;
import com.accenture.domain.model.Branch;

public class ProductBranchPair {
    private final Product product;
    private final Branch branch;

    public ProductBranchPair(Product product, Branch branch) {
        this.product = product;
        this.branch = branch;
    }
    public Product getProduct() { return product; }
    public Branch getBranch() { return branch; }
}
