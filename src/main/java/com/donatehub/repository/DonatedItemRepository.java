package com.donatehub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.donatehub.entity.DonatedItem;

public interface DonatedItemRepository extends JpaRepository<DonatedItem, Long> {
    List<DonatedItem> findByCollectedTrue();
    List<DonatedItem> findByCollectedFalse();
    List<DonatedItem> findByCollectedTrueAndDistributionIsNull();
    List<DonatedItem> findByCategory(DonatedItem.Category category);
    boolean existsByIdAndDistributionIsNotNull(Long itemId);
}
