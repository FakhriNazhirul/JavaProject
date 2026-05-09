package com.project.organix.repository;

import com.project.organix.entity.Transaksi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransaksiRepository extends JpaRepository<Transaksi, Long> {
    // Mencari riwayat transaksi berdasarkan ID User [cite: 138]
    List<Transaksi> findByUserId(Long userId);
}