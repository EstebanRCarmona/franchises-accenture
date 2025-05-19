package com.franchises.infraestructur.driven_rp.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import com.franchises.infraestructur.driven_rp.entity.BranchEntity;

@Repository
public interface IBranchRepository extends ReactiveCrudRepository<BranchEntity, Long> {
}
