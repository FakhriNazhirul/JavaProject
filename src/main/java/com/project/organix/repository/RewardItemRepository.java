package com.project.organix.repository;

import com.project.organix.entity.RewardItem; // Pastikan Entity ini sudah ada
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardItemRepository extends JpaRepository<RewardItem, Long> {
}