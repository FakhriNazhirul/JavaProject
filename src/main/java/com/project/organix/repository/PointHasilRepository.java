package com.project.organix.repository;

import com.project.organix.entity.PointHasil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointHasilRepository extends JpaRepository<PointHasil, Long> {
}