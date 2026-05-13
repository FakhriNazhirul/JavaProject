package com.project.organix.repository;

import com.project.organix.model.KategoriSampah;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KategoriSampahRepository extends JpaRepository<KategoriSampah, Long> {
    List<KategoriSampah> findAll();
}