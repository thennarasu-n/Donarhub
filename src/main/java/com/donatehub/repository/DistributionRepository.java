package com.donatehub.repository;

import com.donatehub.entity.Distribution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistributionRepository extends JpaRepository<Distribution, Long> {
    boolean existsByItemId(Long itemId);
}
