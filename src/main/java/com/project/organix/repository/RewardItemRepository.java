package com.project.organix.repository;

import com.project.organix.model.RewardItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardItemRepository extends JpaRepository<RewardItem, Long> {
    List<RewardItem> findByStockGreaterThan(int stock);
}