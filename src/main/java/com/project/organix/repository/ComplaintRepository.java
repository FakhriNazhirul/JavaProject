package com.project.organix.repository;

import com.project.organix.entity.Complaint; // Pastikan Entity ini sudah ada
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
}