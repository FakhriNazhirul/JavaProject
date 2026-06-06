package com.project.organix.repository;

import com.project.organix.model.TempatPembuangan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TempatPembuanganRepository extends JpaRepository<TempatPembuangan, Long> {
    List<TempatPembuangan> findByStatus(String status);
}
