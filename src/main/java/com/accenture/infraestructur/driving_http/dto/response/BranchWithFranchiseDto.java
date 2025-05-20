package com.accenture.infraestructur.driving_http.dto.response;

import com.accenture.domain.model.Franchise;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchWithFranchiseDto {
    private Long id;
    private String name;
    private Franchise franchise;
}
