package com.accenture.infraestructur.driving_http.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.accenture.domain.model.Branch;
import com.accenture.domain.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductWithBranchDto {
    private Branch branch;
    @JsonIgnoreProperties({"branchId"})
    private Product product;
    
}
