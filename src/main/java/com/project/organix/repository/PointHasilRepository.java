package com.project.organix.repository;

import com.project.organix.model.PointHasil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointHasilRepository extends JpaRepository<PointHasil, Long> {
    List<PointHasil> findByUserId(Long userId);

    List<PointHasil> findByUserIdOrderByCreatedAtDesc(Long userId);
}