package com.project.organix.controller;

import com.project.organix.entity.Transaksi;
import com.project.organix.repository.TransaksiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransaksiController {

    @Autowired
    private TransaksiRepository transaksiRepository;

    // 5. Mengambil seluruh riwayat transaksi di sistem [cite: 120, 137]
    @GetMapping
    public List<Transaksi> getAllTransactions() {
        return transaksiRepository.findAll();
    }

    // 6. Mengambil riwayat setoran khusus satu warga tertentu [cite: 120, 138]
    @GetMapping("/user/{userId}")
    public List<Transaksi> getTransactionsByUserId(@PathVariable Long userId) {
        return transaksiRepository.findByUserId(userId);
    }

    // 7. Mencatat setoran sampah baru [cite: 120, 133]
    @PostMapping
    public Transaksi createTransaction(@RequestBody Transaksi transaksi) {
        // Logika perhitungan poin otomatis idealnya ada di Service, 
        // tapi untuk awal kita simpan data mentahnya dulu[cite: 111, 134].
        return transaksiRepository.save(transaksi);
    }

    // 8. Menghapus catatan transaksi jika terjadi kesalahan input [cite: 120, 142]
    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        transaksiRepository.deleteById(id);
    }
}