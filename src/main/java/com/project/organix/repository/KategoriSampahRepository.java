package com.project.organix.repository;

import com.project.organix.entity.KategoriSampah;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KategoriSampahRepository extends JpaRepository<KategoriSampah, Long> {
    // Interface ini memungkinkan aplikasi mengambil data kategori sampah dari database [cite: 129, 132]
}